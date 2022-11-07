package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba ls ispisuje sve datoteke i direktorije u traženom direktoriju zajedno sa određenim svojstvima.
 * 
 * @author Ana Bagić
 *
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 1)
				throw new IllegalArgumentException("Number of arguments has to be 1.");
			
			Path dir = Paths.get(args.get(0));
			
			if(!Files.exists(dir)) {
				throw new IllegalArgumentException("Directory " + dir.toString() + " does not exist.");
			}
			
			if(!Files.isDirectory(dir)) {
				throw new IllegalArgumentException("File " + dir.toString() + " is not a directory.");
			}
			
			File[] children = dir.toFile().listFiles();
			
			if(children == null) {
				throw new IllegalArgumentException("Path " + dir.toString() + " is not possible to access (do you have a permission).");
			}
			
			for(File file : children) {
				env.writeln(formatLine(file));
			}
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Pomoćna funkcija za formatiranje jedne linije ispisa.
	 * 
	 * @param file datoteka čija se svojstva žele formatirati
	 * @return formatirani string
	 * @throws IOException ako se ne mogu pročitati file attributes
	 */
	private String formatLine(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(file.isDirectory() ? "d" : "-");
		sb.append(file.canRead() ? "r" : "-");
		sb.append(file.canWrite() ? "w" : "-");
		sb.append(file.canExecute() ? "x " : "- ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributes attrs = Files.getFileAttributeView(
				file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				).readAttributes();
		
		sb.append(String.format("%10d ", Files.walk(file.toPath()).mapToLong(p -> p.toFile().length()).sum()));
		sb.append(sdf.format(new Date(attrs.creationTime().toMillis())));
		
		sb.append(" " + file.getName());
		
		return sb.toString();
	}
	
	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command ls lists all directories and files in given diretory.");
		description.add("Command ls takes one argument - directory name (absolute or relative path).");
		description.add("For every directory/file command writes properties, size, creation date and time and directory/file name.");
		
		return Collections.unmodifiableList(description);
	}

}
