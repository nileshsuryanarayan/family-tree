package com.tree.family.suryanarayan.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tree.family.suryanarayan.model.FamilyMember;

import static com.tree.family.suryanarayan.util.Constants.CSV_DELIMITER;
import static com.tree.family.suryanarayan.util.Constants.EMPTY;
import static com.tree.family.suryanarayan.util.Constants.COMMA;

@Component
@Qualifier(value = "fileDatasetReader")
public class DatasetReaderImpl implements DatasetReader {

	@Value("${family.tree.dataset}")
	private String familyTreeDatasetFile;
	
	public List<String> readData() throws IOException {
		System.out.println("============= Reading file content =============");
		List<String> lines = new ArrayList<String>();
		File file = new File(familyTreeDatasetFile);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int lineCount = 0;
		while((line = br.readLine()) != null){
		    //process the line
			if (lineCount > 0) {
				System.out.println(line);
			    lines.add(line);
			}
			lineCount++;
		}
		
		return lines;
	}
	
	public boolean addNewMember(FamilyMember member, int id) throws IOException {
		String spouseIds = member.getSpouseIds().stream().collect(Collectors.joining(COMMA));
		String childrenIds = member.getChildrenIds().stream().collect(Collectors.joining(COMMA));
		
		StringBuilder sb = new StringBuilder();
		sb.append(id).append(CSV_DELIMITER)
		  .append(false).append(CSV_DELIMITER)
		  .append(member.getFirstName()).append(CSV_DELIMITER)
		  .append(member.getLastName()).append(CSV_DELIMITER)
		  .append(member.getGender()).append(CSV_DELIMITER)
		  .append(member.getDateOfBirth()).append(CSV_DELIMITER)
		  .append(member.getDateOfDeah()).append(CSV_DELIMITER)
		  .append(member.isMarried()).append(CSV_DELIMITER)
		  .append(member.getFatherId() != null ? member.getFatherId() : EMPTY).append(CSV_DELIMITER)
		  .append(member.getMotherId() != null ? member.getMotherId() : EMPTY).append(CSV_DELIMITER)
		  .append(spouseIds).append(CSV_DELIMITER)
		  .append(childrenIds);
		
		System.out.println("String record to be added: " + sb.toString());
		
		// Now append this line to the existing file
		appendLine(sb.toString());
		return true;
	}
	
	public void updateExistingMember(FamilyMember member) throws IOException {
		String spouseIds = member.getSpouseIds().stream().collect(Collectors.joining(COMMA));
		String childrenIds = member.getChildrenIds().stream().collect(Collectors.joining(COMMA));
		
		StringBuilder sb = new StringBuilder();
		sb.append(member.getId()).append(CSV_DELIMITER)
		  .append(false).append(CSV_DELIMITER)
		  .append(member.getFirstName()).append(CSV_DELIMITER)
		  .append(member.getLastName()).append(CSV_DELIMITER)
		  .append(member.getGender()).append(CSV_DELIMITER)
		  .append(member.getDateOfBirth()).append(CSV_DELIMITER)
		  .append(member.getDateOfDeah()).append(CSV_DELIMITER)
		  .append(member.isMarried()).append(CSV_DELIMITER)
		  .append(member.getFatherId() != null ? member.getFatherId() : EMPTY).append(CSV_DELIMITER)
		  .append(member.getMotherId() != null ? member.getMotherId() : EMPTY).append(CSV_DELIMITER)
		  .append(spouseIds).append(CSV_DELIMITER)
		  .append(childrenIds);
		
		System.out.println("String record to be updated: " + sb.toString());
		
		// Now update this line in the existing file 
		
	}
	
	/** 
	 * @throws IOException
	 * 
	 * */
	private void appendLine(String line) throws IOException {
		Path path = Paths.get(familyTreeDatasetFile);
//		Path path = Paths.get("D:/Projects/portfolio/data/suryanarayan-family-dataset.csv");
        Files.write(path, 
                List.of(line),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,   // Create file if not exists
                StandardOpenOption.APPEND);  // Append to end of file
	}

}
