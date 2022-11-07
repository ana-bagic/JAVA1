package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * Testiranje rada parsera.
 * 
 * @author Ana Bagić
 *
 */
public class SmartScriptTester {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String filepath = sc.nextLine();
		//String filepath = "src/main/resources/doc1.txt";
		String docBody = null;
		
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(filepath)),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		sc.close();
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
	}

}
