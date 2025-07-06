package com.tree.family.suryanarayan.util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tree.family.suryanarayan.jpa.entity.Person;
import com.tree.family.suryanarayan.jpa.repo.PersonRepository;
import com.tree.family.suryanarayan.service.DatasetReaderImpl;

import static com.tree.family.suryanarayan.util.Constants.CSV_DELIMITER;
import static com.tree.family.suryanarayan.util.Utility.getYear;
import static com.tree.family.suryanarayan.util.Utility.getMonth;
import static com.tree.family.suryanarayan.util.Utility.getDayOfMonth;

/** 
 * This class processes
 * 
 * */
@Component
public class DataProcessor {

	@Autowired
	DatasetReaderImpl csvDataReaderImpl;
	
	@Autowired
	PersonRepository personRepo;
	
	public boolean persistDatatoDb() {
		System.out.println("Strting the Data persist");
		List<String> strData = null;
		try {
			strData = csvDataReaderImpl.readData();
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		if (strData != null && strData.size() > 0) {
			try {
				List<Person> personList = convertData(strData);
				personRepo.truncateTable();
				System.out.println("Table truncated... Now saving the records.");
				personRepo.saveAll(personList);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
		
		return true;
	}
	
	private List<Person> convertData(List<String> strData) {
		List<Person> list = new ArrayList<>();
		
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		
		for(String str: strData) {
			Person person = new Person();
			String[] strArr = str.split(CSV_DELIMITER);
			
			person.setRoot(Boolean.parseBoolean(strArr[1]));
			person.setFirstName(strArr[2]);
			person.setLastName(strArr[3]);
			person.setGender(strArr[4]);
			
			year = getYear(strArr[5]);
			month = getMonth(strArr[5]) == 0 ? 1 : getMonth(strArr[5]);
			dayOfMonth = getDayOfMonth(strArr[5]) == 0 ? 1 : getDayOfMonth(strArr[5]);
			person.setDateOfBirth(LocalDate.of(year, month, dayOfMonth));
			
			year = getYear(strArr[6]);
			month = getMonth(strArr[6]) == 0 ? 1 : getMonth(strArr[6]);
			dayOfMonth = getDayOfMonth(strArr[6]) == 0 ? 1 : getDayOfMonth(strArr[6]);
			person.setDateOfDeath(LocalDate.of(year, month, dayOfMonth));
			
			person.setMarried(Boolean.parseBoolean(strArr[7]));
			person.setFather(Integer.parseInt(strArr[8] == null || strArr[8] == "" ? "-1" : strArr[8]));
			person.setMother(Integer.parseInt(strArr[9] == null || strArr[9] == "" ? "-1" : strArr[9]));
			person.setSpouse(strArr[10]);
			person.setChildren(strArr[11]);
			
			list.add(person);
		}
		
		return list;
	} 
	
}
