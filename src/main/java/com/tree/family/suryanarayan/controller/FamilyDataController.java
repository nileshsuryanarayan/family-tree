package com.tree.family.suryanarayan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tree.family.suryanarayan.model.FamilyMember;
import com.tree.family.suryanarayan.service.FamilyDataService;
import com.tree.family.suryanarayan.util.DataProcessor;

@RestController
@CrossOrigin(origins = { "${service.allowed.origins}" })
public class FamilyDataController {
	
	@Autowired
	private FamilyDataService service;
	
	@Autowired
	private DataProcessor dataProcessor;

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
	
	/** 
	 * */
	@PostMapping("/update-family-member")
	private ResponseEntity<List<FamilyMember>> updateFamilyData(@RequestBody FamilyMember request) {
		List<FamilyMember> list = null;
		try {
			// update Family Member
			System.out.println("Update Family member request" + request);
			if(request == null && request.getId() <= 0) {
				System.out.println("ERROR: INVALID REQUEST");
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			boolean updateSuccess = service.updateFamilyMember(request);
			if (updateSuccess) {
				list = service.getFamilyMembers();
			} else {
				System.out.println("Update Failed :: Returning HTTP Error 500");
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
	
	@GetMapping("/sync-db-with-csv")
	private ResponseEntity<String> syncDbWithCSV() {
		if(dataProcessor.persistDatatoDb()) {
			System.out.println("Successfully PERSISTED.");
			return ResponseEntity.ok("Data persisted to MySQL database successfully!");
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}
	}
	
}
