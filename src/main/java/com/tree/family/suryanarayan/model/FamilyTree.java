package com.tree.family.suryanarayan.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class FamilyTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6860656594126860445L;
	private FamilyMember root;
	
}
