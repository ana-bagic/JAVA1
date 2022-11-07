package hr.fer.zemris.java.hw05.shell.commands;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba cat ispisuje sadržaj tražene datoteke.
 * 
 * @author Ana Bagić
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() < 1 || args.size() > 2)
				throw new IllegalArgumentException("Number of arguments has to be 1 or 2.");
			
			Charset charset = args.size() == 2 ? Charset.forName(args.get(1)) : Charset.defaultCharset();
			
			Path from = Paths.get(args.get(0));
			
			if(!Files.exists(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " does not exist.");
			}
			
			if(!Files.isRegularFile(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " is not a regular file.");
			}
			
			try (InputStream is = Files.newInputStream(from)) {
				byte[] buff = new byte[4096];
				
				while(true) {
					int bytesRead = is.read(buff);
					
					if(bytesRead < 1)
						break;

					env.write(new String(Arrays.copyOf(buff, bytesRead), charset));
				}
			}
			
			env.writeln("");
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command cat writes the content of the given file to the console.");
		description.add("Command cat takes one or two arguments.");
		description.add("First argument is mandatory and is a path to some file (absolute or relative path).");
		description.add("Second argument is optional and is charset name that should"
				+ " be used to interpret chars from bytes. If not provided, default one is used.");
		
		return Collections.unmodifiableList(description);
	}

}
