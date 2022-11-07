package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Razred stvara bazu podataka o studentima i izvršava upite nad njom.
 * 
 * @author Ana Bagić
 *
 */
public class StudentDB {

	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(
				 Paths.get("database.txt"),
				 StandardCharsets.UTF_8);
		
		StudentDatabase database = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNext()) {
			List<StudentRecord> records = new ArrayList<>();
			String query = sc.nextLine().trim();
			if(query.equals("exit"))
				break;
			
			try {
				if(!query.startsWith("query"))
					throw new IllegalArgumentException("Upit mora početi s query");
				
				QueryParser parser = new QueryParser(query.substring(5));
				
				if(parser.isDirectQuery()) {
					StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
					if(r != null) {
						System.out.println("Using index for record retrieval.");
						records.add(r);
					}
				}
				else
					records.addAll(database.filter(new QueryFilter(parser.getQuery())));
				
				if(records.size() > 0)
					RecordFormatter.format(records).forEach(System.out::println);
				System.out.println("Records selected: " + records.size());
				System.out.println();
				
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		}
		
		sc.close();
		System.out.println("Goodbye!");

	}
}
