package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tester za razred StudentDatabase.
 * 
 * @author Ana Bagić
 *
 */
public class StudentDatabaseTester {

	@Test
	public void forJMBAGTest() throws IOException {
		StudentDatabase database = new StudentDatabase(readLines());
		
		List<StudentRecord> fullList = database.filter(record -> true);
		List<StudentRecord> emptyList = database.filter(record -> false);
		
		assertEquals(readLines().size(), fullList.size());
		assertEquals(0, emptyList.size());
	}
	
	@Test
	public void fieldValueGettersTest() throws IOException {
		StudentDatabase database = new StudentDatabase(readLines());
		
		StudentRecord record = database.filter(rec -> true).get(3);
		
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Božić", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000000004", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void conditionalExpressionTest() throws IOException {
		StudentDatabase database = new StudentDatabase(readLines());
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
		);

		List<StudentRecord> filteredList = database.filter(record ->
			expr.getComparisonOperator().satisfied(
					 expr.getFieldGetter().get(record),
					 expr.getStringLiteral()
			)
		);
		
		assertEquals(1, filteredList.size());
		assertEquals("Andrea", filteredList.get(0).getFirstName());
	}
	
	@Test
	public void queryParserTest() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
		assertEquals(2, qp2.getQuery().size());
	}
	
	/**
	 * @return listu stringova koji predstavljaju jedan zapis o studentu
	 * @throws IOException ako se dogodi pogreška pri čitanju iz datoteke
	 */
	private List<String> readLines() throws IOException {
		return Files.readAllLines(
				 Paths.get("database.txt"),
				 StandardCharsets.UTF_8);
	}
}
