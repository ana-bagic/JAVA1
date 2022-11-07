package hr.fer.zemris.java.hw05.shell.commands;

import java.io.InputStream;
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
 * Naredba hexdump ispisuje sadržaj tražene datoteke zajedno sa heksadekadskim oblikom.
 * 
 * @author Ana Bagić
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.extractArguments(arguments);
			if(args.size() != 1)
				throw new IllegalArgumentException("Number of arguments has to be 1.");
			
			Path from = Paths.get(args.get(0));
			
			if(!Files.exists(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " does not exist.");
			}
			
			if(!Files.isRegularFile(from)) {
				throw new IllegalArgumentException("File " + from.toString() + " is not a regular file.");
			}
			
			int currentRow = 0;
			int offset = 0;
			byte[] buff = new byte[4096];
			byte[] tmp = new byte[4096];
			
			try (InputStream is = Files.newInputStream(from)) {
				while(true) {
					int bytesRead = is.read(buff, offset, 4096 - offset);
					
					if(bytesRead < 1)
						break;
					
					bytesRead += offset;

					int fullRows = (int) Math.floor(bytesRead/16);
					offset = bytesRead - fullRows*16;
					
					for(int i = 0; i < fullRows; i++) {
						env.writeln(formatLine(Arrays.copyOfRange(buff, i*16, i*16 + 16), currentRow++));
					}
					
					System.arraycopy(buff, fullRows*16, tmp, 0, offset);
					buff = tmp;
				}
			}
			
			env.writeln(formatLine(Arrays.copyOfRange(buff, 0, offset), currentRow));
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Pomoćna funkcija za formatiranje jedne linije ispisa.
	 * 
	 * @param bytes podaci koje se želi ispisati
	 * @param currentRow trenutna linija ispisa
	 * @return formatirani string
	 */
	private String formatLine(byte[] bytes, int currentRow) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%07X0: ", currentRow));
		for(int i = 0; i < 16; i++) {
			if(i < bytes.length) {
				String hex = Integer.toHexString((int) bytes[i] & 0xff);
				sb.append((hex.length() == 1 ? "0" : "") + hex);
			} else {
				sb.append("  ");
			}
			
			sb.append(i == 7 ? "|" : " ");
		}
		
		sb.append("| ");
		
		for(int i = 0; i < 16; i++) {
			if(i < bytes.length) {
				sb.append((int) bytes[i] < 32 || (int) bytes[i] > 127
						? "."
						: (char) bytes[i]);
			} else {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command hexdump prints file contents with its hexadecimal form.");
		description.add("Command hexdump takes one argument - file name (relative or absolute path) to write.");
		
		return Collections.unmodifiableList(description);
	}

}
