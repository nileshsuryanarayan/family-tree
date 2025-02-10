package com.tree.family.suryanarayan.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class FamilyMember extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 981300952518376312L;
	
	@JsonProperty("isRoot")
	private boolean isRoot;
	private Person father;
	private Person mother;
	private List<Person> spouse;
	private List<Person> children;
	
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public Person getFather() {
		return father;
	}
	public void setFather(Person father) {
		this.father = father;
	}
	public Person getMother() {
		return mother;
	}
	public void setMother(Person mother) {
		this.mother = mother;
	}
	public List<Person> getSpouse() {
		return spouse;
	}
	public void setSpouse(List<Person> spouse) {
		this.spouse = spouse;
	}
	public List<Person> getChildren() {
		return children;
	}
	public void setChildren(List<Person> children) {
		this.children = children;
	}
	
}
