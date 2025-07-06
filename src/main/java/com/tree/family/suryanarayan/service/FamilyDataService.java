package com.tree.family.suryanarayan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tree.family.suryanarayan.model.FamilyMember;
import com.tree.family.suryanarayan.model.Person;
import com.tree.family.suryanarayan.util.Gender;

import static com.tree.family.suryanarayan.util.Constants.CSV_DELIMITER;
import static com.tree.family.suryanarayan.util.Constants.COMMA;
import static com.tree.family.suryanarayan.util.Constants.EMPTY;
import static com.tree.family.suryanarayan.util.Constants.INVLAID_ID_PLACEHOLDER;


@Service
public class FamilyDataService {

	@Autowired
//	@Qualifier(value = "dbDatasetReader")
	@Qualifier(value = "fileDatasetReader")
	private DatasetReader datasetReader;
	
	/** 
	 * 
	 * */
	public List<FamilyMember> getFamilyMembers() throws IOException {
		List<?> lines = datasetReader.readData();
		List<FamilyMember> members = new ArrayList<FamilyMember>();
		for(Object line: lines) {
			FamilyMember member = parseObjToFamilyMember(line);
			members.add(member);
		}
//		generateFamilyTree(members);
		return members;
	}
	
	/** 
	 * 
	 * */
	private FamilyMember parseObjToFamilyMember(Object obj) {
		FamilyMember member = null;
		if(obj instanceof String) {
			member = parseStrIntoFamilyMember((String) obj);
		} else if(obj instanceof com.tree.family.suryanarayan.jpa.entity.Person) {
			member = parsePersonEntityToFamilyMember((com.tree.family.suryanarayan.jpa.entity.Person) obj);
		}
		return member;
	}
	
	/** 
	 * 
	 * */
	public boolean updateFamilyMember(FamilyMember updatedMember) {
		// Save the spouses
		List<String> spouseIds = addSpouses(updatedMember);
		if (spouseIds != null && spouseIds.size() > 0) {
			updatedMember.setSpouseIds(spouseIds);
		}
		
		// Save the children
		List<String> childrenIds = addChildrenIds(updatedMember);
		if (childrenIds != null && childrenIds.size() > 0) {
			updatedMember.setSpouseIds(childrenIds);
		}
		
		try {
			datasetReader.updateExistingMember(updatedMember);
			return true;
		} catch (IOException e) {
			System.out.println("ERROR occurred in updting existing member: " + e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/** 
	 * [0]  - id <br>
	 * [1]  - isRoot <br>
	 * [2]  - FirstName <br>
	 * [3]  - LastName <br>
	 * [4]  - Gender <br>
	 * [5]  - DateOfBirth <br>
	 * [6]  - DateOfDeath <br>
	 * [7]  - IsMarried <br>
	 * [8]  - Father <br>
	 * [9]  - Mother <br>
	 * [10]  - Spouse <br>
	 * [11] - Children <br>
	 * 
	 * */
	private FamilyMember parseStrIntoFamilyMember(String line) {
		FamilyMember member = new FamilyMember();
		if (line != null && !line.trim().equals("")) {
			String[] strArray = line.split(CSV_DELIMITER);
			
			// Set object  details
			member.setId(Integer.parseInt(strArray[0]));
			member.setRoot(Boolean.parseBoolean(strArray[1]));
			member.setFirstName(strArray[2]);
			member.setLastName(strArray[3]);
			member.setGender(strArray[4].equals("male") ? Gender.MALE : Gender.FEMALE);
			member.setDateOfBirth(strArray[5]);
			member.setDateOfDeah(strArray[6]);
			member.setMarried(Boolean.parseBoolean(strArray[7]));
			member.setFatherId(strArray[8]);
			member.setMotherId(strArray[9]);
			member.setSpouseIds(Arrays.asList(strArray[10].split(COMMA)));
			member.setChildrenIds(Arrays.asList(strArray[11].split(COMMA)));
		}
		return member;
	}
	
	/** 
	 * 
	 * */
	private FamilyMember parsePersonEntityToFamilyMember(com.tree.family.suryanarayan.jpa.entity.Person person) {
		FamilyMember member = new FamilyMember();
		String[] emptyStrArray = new String[0];
		if(person != null) {
			// Set object  details
			member.setId(person.getId());
			member.setRoot(person.isRoot());
			member.setFirstName(person.getFirstName());
			member.setLastName(person.getLastName());
			member.setGender(person.getGender().equals("male") ? Gender.MALE : Gender.FEMALE);
			member.setDateOfBirth(person.getDateOfBirth().toString());
			member.setDateOfDeah(person.getDateOfDeath().toString());
			member.setMarried(person.isMarried());
			member.setFatherId(Integer.toString(person.getFather()));
			member.setMotherId(Integer.toString(person.getMother()));
			member.setSpouseIds(Arrays.asList(person.getSpouse() != null ? person.getSpouse().split(COMMA) : emptyStrArray));
			member.setChildrenIds(Arrays.asList(person.getChildren() != null ? person.getChildren().split(COMMA) : emptyStrArray));
		}
		return member;
	}
	
	/** 
	 * 
	 * 
	 * */
	private List<String> addSpouses(FamilyMember updatedMember) {
		List<String> spouseIds = new ArrayList<>();
		List<Integer> errIndices = null;
		if(updatedMember.getSpouse() != null && updatedMember.getSpouse().size() > 0) {
			// Iterate over spouses array
			errIndices = new ArrayList<>();
			int index = 0;
			for(Person spouse: updatedMember.getSpouse()) {
				FamilyMember member = (FamilyMember) spouse;
				member.setMarried(true);
				try {
					int spouseId = getNewId();
					System.out.println("The new Id for spouse is: " + spouseId);
					if(datasetReader.addNewMember(member, spouseId)) {
						spouseIds.add(Integer.toString(spouseId));
					} else {
						// Spouse not added
						System.out.println("ERROR: Spouse Not added");
						errIndices.add(index);
					}
				} catch(IOException e) {
					System.out.println("ERROR occurred in dding  new member: " + e.getLocalizedMessage());
					errIndices.add(index);
				}
				index++;
			}
		}
		if(errIndices != null && errIndices.size() > 0) {
			System.out.println("Spouse Error Indices: " + errIndices);
		}
		return spouseIds;
	}
	
	/** 
	 * 
	 * 
	 * */
	private List<String> addChildrenIds(FamilyMember updatedMember) {
		List<Integer> errIndices = null;
		List<String> childrenIds = new ArrayList<>();
		if(updatedMember.getChildren() != null && updatedMember.getChildren().size() > 0) {
			// Iterate over spouses array
			errIndices = new ArrayList<>();
			int index = 0;
			for(Person child: updatedMember.getChildren()) {
				FamilyMember member = (FamilyMember) child;
				try {
					int childId = getNewId();
					System.out.println("The new Id for child is: " + childId);
					if (datasetReader.addNewMember(member, childId)) {
						childrenIds.add(Integer.toString(childId));
					} else {
						// Child not added
						System.out.println("ERROR: child not added");
						errIndices.add(index);
					}
				} catch(IOException e) {
					System.out.println("ERROR occurred in dding  new member: " + e.getLocalizedMessage());
					errIndices.add(index);
				} catch(IllegalArgumentException e) {
					System.out.println("ERROR occurred in persisting new member in Db: " + e.getLocalizedMessage());
					errIndices.add(index);
				}
				index++;
			}
		}
		if(errIndices != null && errIndices.size() > 0) {
			System.out.println("Children Error Indices: " + errIndices);
		}
		return childrenIds;
	}
	
	/** 
	 * 
	 * 
	 * */
	private int getNewId() throws IOException {
		// First get all the records and then calculate the highest
		Optional<FamilyMember> op = this.getFamilyMembers().stream().max(Comparator.comparingInt(FamilyMember::getId));
		if (op.isPresent())
			return op.get().getId() + 1;
		else
			return -2;
	}
	
}
