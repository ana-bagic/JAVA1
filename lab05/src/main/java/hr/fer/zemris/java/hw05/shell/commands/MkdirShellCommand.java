package hr.fer.zemris.java.hw05.shell.commands;

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
 * Naredba mkdir stvara strukturu direktorija na temelju danog imena.
 * 
 * @author Ana Bagić
 *
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 1)
				throw new IllegalArgumentException("Number of arguments has to be 1.");
			
			Path dir = Paths.get(args.get(0));
			Files.createDirectories(dir);
			
			env.writeln("Directory " + dir.toString() + " created successfully.");
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command mkdir creates appropriate directory structure in current directory from given name.");
		description.add("Command mkdir takes one argument - directory name.");
		
		return Collections.unmodifiableList(description);
	}

}
