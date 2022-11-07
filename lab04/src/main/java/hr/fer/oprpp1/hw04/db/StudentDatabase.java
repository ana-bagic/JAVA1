package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Razred modelira bazu podataka studenata.
 * 
 * @author Ana Bagić
 *
 */
public class StudentDatabase {
	
	/**
	 * Lista svih zapisa o studentima.
	 */
	private List<StudentRecord> studentRecords;
	/**
	 * Index liste zapisa po jmbagu.
	 */
	private Map<String, Integer> jmbagIndex;
	/**
	 * Polje charactera korišteno za parsiranje zapisa o studentu.
	 */
	private char[] data;
	/**
	 * Index polja, korištenog za parsiranje, na kojemu je parser trenutno.
	 */
	private int index;

	/**
	 * Konstruktor prima listu stringova koji predstavljaju zapise o studentima i puni listu zapisima.
	 * 
	 * @param students lista stringova koji predstavljaju zapise o studentima
	 */
	public StudentDatabase(List<String> students) {
		Objects.requireNonNull(students, "Lista studenata ne može biti null");
		
		studentRecords = new ArrayList<>();
		jmbagIndex = new HashMap<>();
		
		for(String student : students) {
			StudentRecord record = parseStudent(student);
			studentRecords.add(record);
			jmbagIndex.put(record.getJmbag(), studentRecords.size() - 1);
		}
	}
	
	/**
	 * Poslani string parsira i pretvara u StudentRecord.
	 * 
	 * @param student string kojega se parsira
	 * @return StudentRecord napravljen iz poslanog stringa
	 */
	private StudentRecord parseStudent(String student) {
		data = student.toCharArray();
		index = 0;
		
		skipWhitespace();
		String jmbag = parseForToken("jmbag");
		if(forJMBAG(jmbag) != null)
			throw new IllegalArgumentException("Jmbag " + jmbag + " već postoji u bazi podataka");
		
		skipWhitespace();
		String name1 = Objects.requireNonNull(parseForToken("name"), "Prezime nije zadano");
		skipWhitespace();
		String name2 = Objects.requireNonNull(parseForToken("name"), "Ime nije zadano");
		skipWhitespace();
		String name3 = parseForToken("name");
		skipWhitespace();
		
		int finalGrade = Integer.parseInt(String.valueOf(data[index]));
		if(finalGrade < 1 || finalGrade > 5)
			throw new IllegalArgumentException("Ocjena mora biti u rasponu od 1 do 5");
		
		return name3 == null
				? new StudentRecord(jmbag, name2, name1, finalGrade)
				: new StudentRecord(jmbag, name3, name1 + " " + name2, finalGrade);
	}

	/**
	 * Vraća string reprezentaciju traženog tokena.
	 * 
	 * @param expectedToken token koji se traži - "jmbag" ili "name"
	 * @return string reprezentaciju traženog tokena
	 */
	private String parseForToken(String expectedToken) {
		StringBuilder sb = new StringBuilder();
		
		while(data.length != index) {
			char nextCh = data[index];
			
			if(expectedToken == "jmbag"
					? Character.isDigit(nextCh)
					: (Character.isLetter(nextCh) || nextCh == '-')) {
				sb.append(nextCh);
				index++;
			}
			else break;
		}
		
		return sb.length() == 0 ? null : sb.toString();
	}

	/**
	 * Preskače sve praznine.
	 */
	private void skipWhitespace() {
		while(data.length != index) {
			if(data[index] == '\t' || data[index] == ' ')
				index++;
			else break;
		}
	}

	/**
	 * Vraća zapis o studentu s poslanim jmbagom ako on postoji, inače <code>null</code>.
	 * Složenost metode je O(1).
	 * 
	 * @param jmbag jmbag studenta čiji zapis želimo dohvatiti
	 * @return zapis o studentu s poslanim jmbagom ako on postoji, inače <code>null</code>
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Integer index = jmbagIndex.get(jmbag);
		return index == null ? null : studentRecords.get(index);
	}
	
	/**
	 * Vraća listu svih zapisa o studentima koji zadovoljavaju poslani filter.
	 * 
	 * @param filter filter koji filtrira zapise o studentima
	 * @return listu svih zapisa o studentima koji zadovoljavaju poslani filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		Objects.requireNonNull(filter, "IFilter ne može biti null");
		
		return studentRecords.stream()
				.filter(filter::accepts)
				.collect(Collectors.toList());
	}

}
