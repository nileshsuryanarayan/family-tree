package com.tree.family.suryanarayan.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatasetReaderImpl implements DatasetReader {

	@Value("${family.tree.dataset}")
	private String familyTreeDatasetFile;
	
	/**
	 * @throws IOException  
	 * 
	 * 
	 * */
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

}
