package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba tree ispisuje traženi direktorij u obliku stabla.
 * 
 * @author Ana Bagić
 *
 */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 1)
				throw new IllegalArgumentException("Number of arguments has to be 1.");
			
			Path directory = Paths.get(args.get(0));
			
			if(!Files.exists(directory)) {
				throw new IllegalArgumentException("Directory " + directory.toString() + " does not exist.");
			}
			
			if(!Files.isDirectory(directory)) {
				throw new IllegalArgumentException("File " + directory.toString() + " is not a directory.");
			}
			
			Files.walkFileTree(directory, new FileVisitor<Path>() {

				private int spaceCounter = 0;
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(spaceCounter) + dir.toFile().getName());
					
					spaceCounter += 2;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(spaceCounter) + file.toFile().getName());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					spaceCounter -= 2;
					return FileVisitResult.CONTINUE;
				}
				
			});
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command tree prints given directory as a tree.");
		description.add("Command cat takes one argument - directory name (absolute or relative path).");
		
		return Collections.unmodifiableList(description);
	}

}
