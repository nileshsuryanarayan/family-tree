package com.tree.family.suryanarayan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tree.family.suryanarayan.model.FamilyMember;
import com.tree.family.suryanarayan.service.FamilyDataService;

@RestController
@CrossOrigin(origins = { "${service.allowed.origins}" })
public class FamilyDataController {
	
	@Autowired
	private FamilyDataService service;

	@GetMapping("/family-members")
	private ResponseEntity<List<FamilyMember>> getFamilyMembers() {
		List<FamilyMember> list = null;
		try {
			list = service.getFamilyMembers();
		} catch(IOException e) {
			System.out.println("ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(list == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			return ResponseEntity.ok(list);
		}
	}
	
}
