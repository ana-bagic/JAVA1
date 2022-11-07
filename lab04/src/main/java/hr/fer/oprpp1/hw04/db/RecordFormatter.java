package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class RecordFormatter {

	public static List<String> format(List<StudentRecord> records) {
		OptionalInt longestFirstName = records.stream()
					.mapToInt(record -> record.getFirstName().length())
					.max();
		OptionalInt longestLastName = records.stream()
				.mapToInt(record -> record.getLastName().length())
				.max();
		
		List<String> forPrint = new ArrayList<>();
		StringBuilder sbHF = new StringBuilder();
		
		sbHF.append("+============+");
		for(int i = 0; i < longestLastName.getAsInt() + 2; i++)
			sbHF.append("=");
		sbHF.append("+");
		for(int i = 0; i < longestFirstName.getAsInt() + 2; i++)
			sbHF.append("=");
		sbHF.append("+===+");
		
		forPrint.add(sbHF.toString());
		
		for(StudentRecord record : records) {
			int nrSpaceLastName = longestLastName.getAsInt() - record.getLastName().length();
			int nrSpaceFirstName = longestFirstName.getAsInt() - record.getFirstName().length();
			StringBuilder sb = new StringBuilder();
			
			sb.append("| ").append(record.getJmbag()).append(" | ").append(record.getLastName());
			for(int i = 0; i < nrSpaceLastName; i++)
				sb.append(" ");
			sb.append(" | ").append(record.getFirstName());
			for(int i = 0; i < nrSpaceFirstName; i++)
				sb.append(" ");
			sb.append(" | ").append(record.getFinalGrade()).append(" |");
			
			forPrint.add(sb.toString());
		}
		
		forPrint.add(sbHF.toString());
		
		return forPrint;
	}

}
