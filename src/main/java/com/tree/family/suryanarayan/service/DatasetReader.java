package com.tree.family.suryanarayan.service;

import java.io.IOException;
import java.util.List;

import com.tree.family.suryanarayan.model.FamilyMember;

public interface DatasetReader {

	/**
	 * @return List<String>
	 * 
	 * @throws IOException  
	 * 
	 * 
	 * */
	public List<?> readData() throws IOException;
	
	/** 
	 * 
	 * @param member of type {@link FamilyMember} 
	 * @param id
	 * 
	 * @throws IOException
	 * 
	 * @author Nilesh Babasaheb Suryanarayan
	 * 
	 * @return true if record successfully added, false otherwise
	 * 
	 * <br><br>
	 * @implNote
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
	public boolean addNewMember(FamilyMember member, int id) throws IOException;
	
	/**
	 * @param member of type {@link FamilyMember} 
	 * 
	 * @author Nilesh Babasaheb Suryanarayan
	 * 
	 * @throws IOException
	 * 
	 * <br><br>
	 * @implNote
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
	public void updateExistingMember(FamilyMember member) throws IOException;
	
}
