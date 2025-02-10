package com.tree.family.suryanarayan.model;

import java.io.Serializable;
import java.util.List;

import com.tree.family.suryanarayan.util.Gender;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803516537001511137L;
	private int id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String dateOfBirth;
	private String dateOfDeah;
	private boolean isMarried;
	private String fatherId;
	private String motherId;
	private List<String> spouseIds;
	private List<String> childrenIds;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfDeah() {
		return dateOfDeah;
	}
	public void setDateOfDeah(String dateOfDeah) {
		this.dateOfDeah = dateOfDeah;
	}
	public boolean isMarried() {
		return isMarried;
	}
	public void setMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	public String getMotherId() {
		return motherId;
	}
	public void setMotherId(String motherId) {
		this.motherId = motherId;
	}
	public List<String> getSpouseIds() {
		return spouseIds;
	}
	public void setSpouseIds(List<String> spouseIds) {
		this.spouseIds = spouseIds;
	}
	public List<String> getChildrenIds() {
		return childrenIds;
	}
	public void setChildrenIds(List<String> childrenIds) {
		this.childrenIds = childrenIds;
	}
	
	
}
