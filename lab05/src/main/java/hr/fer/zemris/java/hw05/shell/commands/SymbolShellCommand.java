package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Util;

/**
 * Naredba symbol ispisuje ili mijenja znakove ljuske za početak naredbe, te označavanje da se naredbe protežu kroz više redova.
 * 
 * @author Ana Bagić
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() < 1 || args.size() > 2)
				throw new IllegalArgumentException("Number of arguments has to be 1 or 2.");
			
			char symbol;
			
			switch (args.get(0)) {
			case "PROMPT" -> {
				symbol = env.getPromptSymbol();
				if(args.size() == 2)
					env.setPromptSymbol(args.get(1).charAt(0));
			}
			case "MORELINES" -> {
				symbol = env.getMorelinesSymbol();
				if(args.size() == 2)
					env.setMorelinesSymbol(args.get(1).charAt(0));
			}
			case "MULTILINE" -> {
				symbol = env.getMultilineSymbol();
				if(args.size() == 2)
					env.setMultilineSymbol(args.get(1).charAt(0));
			}
			default ->
			throw new IllegalArgumentException("Unexpected value: " + args.get(0) + ".");
			}
			
			env.writeln("Symbol for " + args.get(0) + (args.size() == 1
					? " is '" + symbol + "'"
					: " changed from '" + symbol + "' to '" + args.get(1) + "'"));
				
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command symbol writes or changes symbol used to mark beginning or end of input line.");
		description.add("Command symbol takes one or two arguments.");
		description.add("First argument is mandatory and is a name of symbol that should be written or changed - PROMPT, MORELINES, MULTILINE.");
		description.add("PROMPT - symbol that is always on the beginning of first line.");
		description.add("MORELINES - symbol that marks end of line when there are more lines in command.");
		description.add("MULTILINE - symbol that is located on the beginning of multiline command except the first line.");
		description.add("Second argument is optional and is new symbol that the current one should be changed to.");
		
		return Collections.unmodifiableList(description);
	}

}
