package hr.fer.zemris.java.hw05.shell.commands;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba copy kopira sadržaj jedne datoteke u drugu.
 * 
 * @author Ana Bagić
 *
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 2)
				throw new IllegalArgumentException("Number of arguments has to be 2.");
			
			Path from = Paths.get(args.get(0));
			Path to = Paths.get(args.get(1));
			
			if(!Files.exists(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " does not exist.");
			}
			
			if(!Files.isRegularFile(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " is not a regular file.");
			}
			
			if(Files.isDirectory(to)) {
				to = to.resolve(from.getFileName());
			}
			
			if(Files.exists(to)) {
				env.writeln("File " + to + " already exists. Do you want to overwrite it? (y/n)");
				env.write(env.getPromptSymbol() + " ");
				
				if(!env.readLine().equals("y")) {
					env.writeln("Command terminated.");
					return ShellStatus.CONTINUE;
				}
			}
			
			try (InputStream is = Files.newInputStream(from);
					OutputStream os = Files.newOutputStream(to)) {
				byte[] buff = new byte[4096];
				
				while(true) {
					int bytesRead = is.read(buff);
					
					if(bytesRead < 1)
						break;

					os.write(buff, 0, bytesRead);
				}
			}
			
			env.writeln("Copy sucessful.");
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command copy copies contents of one file to another.");
		description.add("Command copy takes two arguments.");
		description.add("First argument is a path to source file (absolute or relative path).");
		description.add("Second argument is a path to destination file"
				+ " or directory (absolute or relative path) in which case new file with the same name as source file is created.");
		description.add("If destination file exists user is asked if file should be overwritten.");
		
		return Collections.unmodifiableList(description);
	}

}
