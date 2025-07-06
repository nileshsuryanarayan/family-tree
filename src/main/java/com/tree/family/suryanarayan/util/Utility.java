package com.tree.family.suryanarayan.util;

import java.util.List;

/** 
 * Utility cls
 * */
public class Utility {
	
	/** 
	 * @param list - List of String
	 * 
	 * <br>
	 * This method takes a list of String and coallate them into one 
	 * comma separated String
	 * <br>
	 * 
	 * @return String
	 * */
	public static String coallateIntoOneStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		list.stream().forEach(elem -> sb.append(elem));
		System.out.println("DEBUG:: List_Of_String_To_String: " + sb.toString());
		return sb.toString();
	}

	public static int getYear(String str) {
		return getIntArr(str)[2];
	}
	
	public static int getMonth(String str) {
		return getIntArr(str)[1];
	}
	
	public static int getDayOfMonth(String str) {
		return getIntArr(str)[0];
	}
	
	/** 
	 * [0] - Day Of Month<br>
	 * [1] - Month<br>
	 * [2] - Year<br>
	 * 
	 * */
	private static int[] getIntArr(String str) {
		String[] arr = str.split("/");
		int[] intArr = new int[arr.length];
		for(int i=0; i<arr.length; i++) {
			intArr[i] = Integer.parseInt(arr[i]);
		}
		return intArr;
	}
	
}
