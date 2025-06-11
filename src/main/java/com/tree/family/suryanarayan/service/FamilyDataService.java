package com.tree.family.suryanarayan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tree.family.suryanarayan.model.FamilyMember;
import com.tree.family.suryanarayan.model.FamilyTree;
import com.tree.family.suryanarayan.model.Person;
import com.tree.family.suryanarayan.util.Gender;

import static com.tree.family.suryanarayan.util.Constants.CSV_DELIMITER;
import static com.tree.family.suryanarayan.util.Constants.COMMA;
import static com.tree.family.suryanarayan.util.Constants.EMPTY;
import static com.tree.family.suryanarayan.util.Constants.INVLAID_ID_PLACEHOLDER;


@Service
public class FamilyDataService {

	@Autowired
	private DatasetReader datasetReader;
	
	/** 
	 * 
	 * */
	public List<FamilyMember> getFamilyMembers() throws IOException {
		List<String> lines = datasetReader.readData();
		List<FamilyMember> members = new ArrayList<FamilyMember>();
		for(String line: lines) {
			FamilyMember member = parseStrIntoFamlyMember(line);
			members.add(member);
		}
//		generateFamilyTree(members);
		return members;
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
	 * 
	 * */
	private FamilyTree generateFamilyTree(List<FamilyMember> members) {
		FamilyMember rootElement = null;
		Map<Integer, FamilyMember> map = new HashMap<Integer, FamilyMember>();
		// Create a Map for each Id and its object
		for(FamilyMember member: members) {
			map.put(member.getId(), member);
		}
		
		// Loop again on members
		for(FamilyMember member: members) {
			// Set Father
			String id = member.getFatherId();
			if(id != null && !id.equals(EMPTY)) {
				// Deep Copy Father Details and
				FamilyMember shallow = map.get(Integer.parseInt(id));
				FamilyMember fatherObj = new FamilyMember();
				fatherObj.setId(shallow.getId());
				fatherObj.setFirstName(shallow.getFirstName());
				fatherObj.setLastName(shallow.getLastName());
				fatherObj.setDateOfBirth(shallow.getDateOfBirth());
				fatherObj.setDateOfDeah(shallow.getDateOfDeah());
				fatherObj.setGender(shallow.getGender());
				fatherObj.setMarried(shallow.isMarried());
				fatherObj.setMother(shallow.getMother());
				fatherObj.setFather(shallow.getFather());
				fatherObj.setSpouse(shallow.getSpouse());
				member.setFather(fatherObj);
			}
			// Set Mother
			id = member.getMotherId();
			if(id != null && !id.equals(EMPTY)) {
				// Deep Copy Father Details and
				FamilyMember shallow = map.get(Integer.parseInt(id));
				FamilyMember motherObj = new FamilyMember();
				motherObj.setId(shallow.getId());
				motherObj.setFirstName(shallow.getFirstName());
				motherObj.setLastName(shallow.getLastName());
				motherObj.setDateOfBirth(shallow.getDateOfBirth());
				motherObj.setDateOfDeah(shallow.getDateOfDeah());
				motherObj.setGender(shallow.getGender());
				motherObj.setMarried(shallow.isMarried());
				motherObj.setMother(shallow.getMother());
				motherObj.setFather(shallow.getFather());
				motherObj.setSpouse(shallow.getSpouse());
				
				member.setMother(motherObj);
			}
			// Set Spouse/s
			if(member.getSpouseIds() != null && member.getSpouseIds().size()>0) {
				List<Person> spList = new LinkedList<Person>();
				for(String spId: member.getSpouseIds()) {
					FamilyMember mmbr = map.get(Integer.parseInt(spId != null && !spId.equals(EMPTY) ? spId : INVLAID_ID_PLACEHOLDER));
					spList.add(mmbr);
				}
				member.setSpouse(spList);
			}
			// Set Children
			if(member.getChildrenIds() != null && member.getChildrenIds().size()>0) {
				List<Person> childList = new LinkedList<Person>();
				for(String chId: member.getChildrenIds()) {
					FamilyMember mmbr = map.get(Integer.parseInt(chId != null && !chId.equals(EMPTY) ? chId : INVLAID_ID_PLACEHOLDER));
					childList.add(mmbr);
				}
				member.setChildren(childList);
			}
			
			if (member.isRoot()) {
				rootElement = member;
			}
		}
		
		System.out.println("Family Members Restructured: =========================");
		System.out.println(members);
		
		FamilyTree familyTree = new FamilyTree();
		familyTree.setRoot(rootElement);
		
		return familyTree;
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
	private FamilyMember parseStrIntoFamlyMember(String line) {
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
