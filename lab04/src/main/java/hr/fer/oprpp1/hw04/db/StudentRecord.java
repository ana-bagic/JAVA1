package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Razred modelira jedan zapis o studentu.
 * 
 * @author Ana Bagić
 *
 */
public class StudentRecord {

	/**
	 * Jmbag studenta.
	 */
	private String jmbag;
	/**
	 * Ime studenta.
	 */
	private String firstName;
	/**
	 * Prezime studenta.
	 */
	private String lastName;
	/**
	 * Završna ocjena studenta.
	 */
	private int finalGrade;
	
	/**
	 * Stvara zapis o studentu na temelju poslanih parametara.
	 * 
	 * @param jmbag jmbag studenta
	 * @param firstName ime studenta
	 * @param lastName prezime studenta
	 * @param finalGrade završna ocjena studenta
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = Objects.requireNonNull(jmbag, "JMBAG nije upisan");
		this.firstName = Objects.requireNonNull(firstName, "Ime nije upisano");
		this.lastName = Objects.requireNonNull(lastName, "Prezime nije upisano");
		this.finalGrade = finalGrade;
	}

	/**
	 * @return jmbag studenta
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return završna ocjenu studenta
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return jmbag + " " + firstName + " "+ lastName + " " + finalGrade;
	}
	
}
