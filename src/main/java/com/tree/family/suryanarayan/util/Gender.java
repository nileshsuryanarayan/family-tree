package com.tree.family.suryanarayan.util;

public enum Gender {
	MALE("MALE"), FEMALE("FEMALE");
	private String value;
	
	@Override
	public String toString() {
		return value;
	}
	
	public String getResponse() {
        return value;
    }

	Gender(String value){
        this.value = value;
    }
}
