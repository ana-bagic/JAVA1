package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeShellCommand;

/**
 * Razred predstavlja ljusku u kojoj se mogu koristiti razne naredbe za rad s datotekama.
 * 
 * @author Ana Bagić
 *
 */
public class MyShell {

	public static void main(String[] args) {
		
		SortedMap<String, ShellCommand> commandsMap = new TreeMap<>();
		commandsMap.put("cat", new CatShellCommand());
		commandsMap.put("charsets", new CharsetsShellCommand());
		commandsMap.put("copy", new CopyShellCommand());
		commandsMap.put("exit", new ExitShellCommand());
		commandsMap.put("help", new HelpShellCommand());
		commandsMap.put("hexdump", new HexdumpShellCommand());
		commandsMap.put("ls", new LsShellCommand());
		commandsMap.put("mkdir", new MkdirShellCommand());
		commandsMap.put("symbol", new SymbolShellCommand());
		commandsMap.put("tree", new TreeShellCommand());
		
		Scanner sc = new Scanner(System.in);
		
		Environment env = new Environment() {
			
			/**
			 * Znak koji se nalazi na početku prve linije naredbe.
			 */
			private char promptSymbol = '>';
			/**
			 * Znak koji se nalazi na početku svake linije (osim prve) koja je dio višelinijske naredbe.
			 */
			private char multiLinesSymbol = '|';
			/**
			 * Znak koji se stavlja na kraj linije za informiranje ljuske da je linija dio višelinijske naredbe.
			 */
			private char moreLinesSymbol = '\\';
			/**
			 * Mapa svih dozvoljenih naredbi.
			 */
			private SortedMap<String, ShellCommand> commands = Collections.unmodifiableSortedMap(commandsMap);
			
			@Override
			public String readLine() throws ShellIOException {
				String nextLine;
				
				try {
					nextLine = sc.nextLine();
				} catch (NoSuchElementException | IllegalStateException e) {
					throw new ShellIOException(e.getMessage());
				}
				
				return nextLine;
			}
			
			@Override
			public void writeln(String text) throws ShellIOException {
				write(text + "\n");
			}
			
			@Override
			public void write(String text) throws ShellIOException {
				try {
					System.out.print(text);
				} catch (Exception e) {
					throw new ShellIOException(e.getMessage());
				}
				
			}
			
			@Override
			public SortedMap<String, ShellCommand> commands() {
				return commands;
			}
			
			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = Objects.requireNonNull(symbol);
			}
			
			@Override
			public void setMultilineSymbol(Character symbol) {
				multiLinesSymbol = Objects.requireNonNull(symbol);
			}
			
			@Override
			public void setMorelinesSymbol(Character symbol) {
				moreLinesSymbol = Objects.requireNonNull(symbol);
			}
			
			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}
			
			@Override
			public Character getMultilineSymbol() {
				return multiLinesSymbol;
			}
			
			@Override
			public Character getMorelinesSymbol() {
				return moreLinesSymbol;
			}

		};
		
		env.writeln("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;
		
		do {
			String input = readLines(env);
			
			Scanner stringScanner = new Scanner(input);
			String commandName = stringScanner.next(), arguments = "";
			if(stringScanner.hasNext())
				arguments = stringScanner.nextLine().trim();
			stringScanner.close();
			
			ShellCommand command = env.commands().get(commandName);
			
			if(command == null) {
				env.writeln("Command " + commandName + " does not exist.");
				continue;
			}
			
			status = command.executeCommand(env, arguments);
		} while (status != ShellStatus.TERMINATE);
		
		sc.close();
		
	}
	
	/**
	 * Pomoćna funkcija koja čita sve linije jedne naredbe.
	 * 
	 * @param env okruženje ljuske
	 * @return sve linije jedne naredbe
	 */
	private static String readLines(Environment env) {
		StringBuilder sb = new StringBuilder();
		String nextLine;
		
		env.write(env.getPromptSymbol() + " ");
		
		do {
			nextLine = env.readLine();

			if(!nextLine.endsWith(" " + env.getMorelinesSymbol())) {
				sb.append(nextLine);
				break;
			}
			
			sb.append(nextLine.substring(0, nextLine.length() - 1));
			env.write(env.getMultilineSymbol() + " ");
			
		} while(true);
		
		return sb.toString();
	}
}
