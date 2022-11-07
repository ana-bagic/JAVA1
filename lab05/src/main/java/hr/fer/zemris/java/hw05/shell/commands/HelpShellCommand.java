package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba help ispisuje listu imena podržanih naredbi, odnosno opis tražene naredbe..
 * 
 * @author Ana Bagić
 *
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() > 1)
				throw new IllegalArgumentException("Number of arguments has to be 0 or 1.");
			
			if(args.size() == 0) {
				for(ShellCommand command : env.commands().values())
					env.writeln(command.getCommandName());
			} else {
				ShellCommand command = env.commands().get(args.get(0));
				if(command == null)
					throw new IllegalArgumentException("Command " + args.get(0) + " is not supported.");
				
				env.writeln(command.getCommandName());
				command.getCommandDescription().forEach(env::writeln);
			}
				
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command help lists names of all supported commands or description of wanted command.");
		description.add("Command help takes 0 or 1 argument.");
		description.add("If no arguments are given, command lists names of all supported commands.");
		description.add("If 1 argument is given - name of command, command writes name and description of wanted command.");
		
		return Collections.unmodifiableList(description);
	}

}
