package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * Razred implementira sučelje LSystemBuilder koje konfigurira i stvara konkretan Lindermayerov sustav.
 * 
 * @author Ana Bagić
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Registrirane produkcije.
	 */
	private Dictionary<Character, String> productions = new Dictionary<>();
	/**
	 * Registrirane akcije.
	 */
	private Dictionary<Character, Command> actions = new Dictionary<>();
	/**
	 * Duljina koraka.
	 */
	private double unitLength = 0.1;
	/**
	 * Faktor s kojim se skalira duljina koraka.
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Početna pozicija kornjače.
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Kut za koji je kornjača zaokrenuta.
	 */
	private double angle = 0;
	/**
	 * Aksiom.
	 */
	private String axiom = "";

	
	@Override
	public LSystem build() {
		return new LSystem() {
			
			@Override
			public String generate(int arg0) {
				if(arg0 == 0)
					return axiom;
				
				StringBuilder sb = new StringBuilder();
				String production;
				
				for(char ch : generate(arg0 - 1).toCharArray()) {
					production = productions.get(ch);
					
					if(production != null)
						sb.append(production);
					else
						sb.append(ch);
				}
				
				return sb.toString();
			}
			
			@Override
			public void draw(int arg0, Painter arg1) {
				Context ctx = new Context();
				Vector2D newDirection = new Vector2D(1, 0).rotated(angle * (Math.PI/180));
				ctx.pushState(new TurtleState(origin, newDirection, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, arg0)));
				
				String generatedString = generate(arg0);
				for(char ch : generatedString.toCharArray()) {
					Command cmd = actions.get(ch);
					
					if(cmd != null)
						cmd.execute(ctx, arg1);
				}
			}
		};
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String line : arg0) {
			if (line.trim().equals(""))
				continue;
			
			String[] tokens = line.split("\\s+");
			
			try {
				switch (tokens[0]) {
				case "origin" -> setOrigin(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
				case "angle" -> setAngle(Double.parseDouble(tokens[1]));
				case "unitLength" -> setUnitLength(Double.parseDouble(tokens[1]));
				case "unitLengthDegreeScaler" -> {
					if(tokens.length == 2 && !tokens[1].contains("/"))
						setUnitLengthDegreeScaler(Double.parseDouble(tokens[1]));
					else if(line.contains("/") && line.indexOf('/') == line.lastIndexOf('/'))
						setUnitLengthDegreeScaler(parseFraction(line));
					else throw new IllegalArgumentException();
				}
				case "command" -> {
					if(tokens[1].length() != 1 || productions.get(tokens[1].charAt(0)) != null)
						throw new IllegalArgumentException();
					
					String substrEndCommand = line.substring(line.indexOf("command") + 7);
					String commandString = substrEndCommand.substring(substrEndCommand.indexOf(tokens[1]) + 1);
					registerCommand(tokens[1].charAt(0), commandString);
				}
				case "axiom" -> setAxiom(tokens[1]);
				case "production" -> {
					if(tokens[1].length() != 1 || productions.get(tokens[1].charAt(0)) != null)
						throw new IllegalArgumentException();
					
					registerProduction(tokens[1].charAt(0), tokens[2]);
				}
				default -> throw new IllegalArgumentException();
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Neispravna naredba: " + line);
			}
		}
		
		return this;
	}

	/**
	 * @param line string koji želimo parsirati
	 * @return double broj koji je rezultat parsiranja razlomka u predanom stringu
	 */
	private double parseFraction(String line) {
		char[] data = line.toCharArray();
		int index = 0;
		
		char nextChar = data[index];
		while(!Character.isDigit(nextChar))
			nextChar = data[++index];
		
		StringBuilder sb1 = new StringBuilder();
		while(nextChar == '.' || Character.isDigit(nextChar)) {
			sb1.append(nextChar);
			nextChar = data[++index];
		}
		Double first = Double.parseDouble(sb1.toString());
		
		while(nextChar == '/' || nextChar == ' ' || nextChar == '\t')
			nextChar = data[++index];
		
		StringBuilder sb2 = new StringBuilder();
		while(index != data.length-1 && (nextChar == '.' || Character.isDigit(nextChar))) {
			sb2.append(nextChar);
			nextChar = data[++index];
		}
		Double second = Double.parseDouble(sb2.toString());
		
		return first/second;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		actions.put(Character.valueOf(arg0), parseCommand(arg1));
		return this;
	}

	/**
	 * Prima string i iz njega parsira i vraća novu komandu.
	 * 
	 * @param arg1 string kojega parsira
	 * @return nova komanda iz predanog stringa
	 * @throws IllegalArgumentException ako se predani string ne može parsirati u neku naredbu
	 */
	private Command parseCommand(String arg1) {
		String[] command = arg1.trim().split("\\s+");
		
		try {
			switch (command[0]) {
			case "draw" : return new DrawCommand(Double.parseDouble(command[1]));
			case "skip" : return new SkipCommand(Double.parseDouble(command[1]));
			case "scale" : return new ScaleCommand(Double.parseDouble(command[1]));
			case "rotate" : return new RotateCommand(Double.parseDouble(command[1]));
			case "push" : return new PushCommand();
			case "pop" : return new PopCommand();
			case "color" : return new ColorCommand(Color.decode("0x" + command[1]));
			default : throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Neispravna naredba: " + command.toString());
		}
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(Character.valueOf(arg0), arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
