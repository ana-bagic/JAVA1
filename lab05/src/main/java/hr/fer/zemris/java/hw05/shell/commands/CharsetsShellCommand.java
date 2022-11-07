package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba charsets ispisuje listu imena podržanih charsetova za ovu Java platformu.
 * 
 * @author Ana Bagić
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 0)
				throw new IllegalArgumentException("There cannot be any arguments.");
			
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			
			for(String name : charsets.keySet()) {
				env.writeln(name);
			}
				
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charset";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command charsets lists names of supported charsets for this Java platform.");
		description.add("Command charsets does not take any arguments.");
		
		return Collections.unmodifiableList(description);
	}

}
