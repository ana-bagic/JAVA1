package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * Sučelje modelira okruženje ljuske pomoću kojega naredbe mogu komunicirati s korisnikom.
 * 
 * @author Ana Bagić
 *
 */
public interface Environment {

	/**
	 * Čita iduću liniju teksta koju je korisnik unio.
	 * 
	 * @return iduća linija teksta koju je korisnik unio
	 * @throws ShellIOException ako se dogodi greška kod čitanja iz ljuske
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Piše u ljusku poslanu poruku.
	 * 
	 * @param text poruka koja se želi ispisati korisniku u ljusku
	 * @throws ShellIOException ako se dogodi greška kod pisanja u ljusku
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Piše u ljusku poslanu poruku te prelazi u novi red.
	 * 
	 * @param text poruka koja se želi ispisati korisniku u ljusku
	 * @throws ShellIOException ako se dogodi greška kod pisanja u ljusku
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Vraća read-only popis svih naredbi.
	 * 
	 * @return read-only popis svih naredbi
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Vraća znak koji se nalazi na početku svake linije (osim prve) koja je dio višelinijske naredbe.
	 * 
	 * @return znak koji se nalazi na početku svake linije (osim prve) koja je dio višelinijske naredbe
	 */
	Character getMultilineSymbol();
	
	/**
	 * Postavlja znak koji se nalazi na početku svake linije (osim prve) koja je dio višelinijske naredbe.
	 * 
	 * @param symbol željeni znak koji se nalazi na početku svake linije (osim prve) koja je dio višelinijske naredbe
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Vraća znak koji se nalazi na početku prve linije naredbe.
	 * 
	 * @return znak koji se nalazi na početku prve linije naredbe
	 */
	Character getPromptSymbol();
	
	/**
	 * Postavlja znak koji se nalazi na početku prve linije naredbe.
	 * 
	 * @param željeni znak koji se nalazi na početku prve linije naredbe
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Vraća znak koji se stavlja na kraj linije za informiranje ljuske da je linija dio višelinijske naredbe.
	 * 
	 * @return znak koji se stavlja na kraj linije za informiranje ljuske da je linija dio višelinijske naredbe
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Postavlja znak koji se stavlja na kraj linije za informiranje ljuske da je linija dio višelinijske naredbe.
	 * 
	 * @param symbol željeni znak koji se stavlja na kraj linije za informiranje ljuske da je linija dio višelinijske naredbe
	 */
	void setMorelinesSymbol(Character symbol);
}
