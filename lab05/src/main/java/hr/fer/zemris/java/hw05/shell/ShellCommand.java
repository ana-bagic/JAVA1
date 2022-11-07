package hr.fer.zemris.java.hw05.shell;

import java.util.List;

/**
 * Sučelje modelira apstraktnu naredbu u ljusci.
 * 
 * @author Ana Bagić
 *
 */
public interface ShellCommand {

	/**
	 * Izvršava naredbu, te vraća stanje ljuske.
	 * 
	 * @param env trenutno okruženje ljuske
	 * @param arguments argumenti naredbe
	 * @return stanje ljuske nakon izvršavanje naredbe
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Vraća ime naredbe.
	 * 
	 * @return ime naredbe
	 */
	String getCommandName();
	
	/**
	 * Vraća opis i upute za korištenje naredbe.
	 * 
	 * @return read-only opis i upute za korištenje naredbe
	 */
	List<String> getCommandDescription();

}
