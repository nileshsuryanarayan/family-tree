package com.tree.family.suryanarayan.service;

import static com.tree.family.suryanarayan.util.Utility.getDayOfMonth;
import static com.tree.family.suryanarayan.util.Utility.getMonth;
import static com.tree.family.suryanarayan.util.Utility.getYear;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tree.family.suryanarayan.jpa.entity.Person;
import com.tree.family.suryanarayan.jpa.repo.PersonRepository;
import com.tree.family.suryanarayan.model.FamilyMember;

import static com.tree.family.suryanarayan.util.Utility.coallateIntoOneStr;

@Component
@Qualifier(value = "dbDatasetReader")
public class SqlDbDataReaderImpl implements DatasetReader {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<?> readData() throws IOException {
		return (List<?>) personRepository.findAll();
	}

	@Override
	public boolean addNewMember(FamilyMember member, int id) throws IllegalArgumentException {
		System.out.println("DEBUG:: Record to be added is being prepared");
		Person personToPersist = new Person();
		
		personToPersist.setRoot(member.isRoot());
		personToPersist.setFirstName(member.getFirstName());
		personToPersist.setLastName(member.getLastName());
		personToPersist.setGender(member.getGender().toString());
		
		int year = getYear(member.getDateOfBirth());
		int month = getMonth(member.getDateOfBirth()) == 0 ? 1 : getMonth(member.getDateOfBirth());
		int dayOfMonth = getDayOfMonth(member.getDateOfBirth()) == 0 ? 1 : getDayOfMonth(member.getDateOfBirth());
		personToPersist.setDateOfBirth(LocalDate.of(year, month, dayOfMonth));
		
		year = getYear(member.getDateOfDeah());
		month = getMonth(member.getDateOfDeah()) == 0 ? 1 : getMonth(member.getDateOfDeah());
		dayOfMonth = getDayOfMonth(member.getDateOfDeah()) == 0 ? 1 : getDayOfMonth(member.getDateOfDeah());
		personToPersist.setDateOfDeath(LocalDate.of(year, month, dayOfMonth));
		
		personToPersist.setMarried(member.isMarried());
		personToPersist.setFather(Integer.parseInt(member.getFatherId() == null || member.getFatherId() == "" ? "-1" : member.getFatherId()));
		personToPersist.setMother(Integer.parseInt(member.getMotherId() == null || member.getMotherId() == "" ? "-1" : member.getMotherId()));
		personToPersist.setSpouse(coallateIntoOneStr(member.getSpouseIds()));
		personToPersist.setChildren(coallateIntoOneStr(member.getChildrenIds()));
		
		personRepository.save(personToPersist);
		System.out.println("DEBUG:: Record added successfully, firstName=" + personToPersist.getFirstName() + ", lastName=" + personToPersist.getLastName());
		return true;
	}

	@Override
	public void updateExistingMember(FamilyMember member) throws IOException {
		int id = member.getId();
		personRepository.findById(id).ifPresentOrElse(
				(person) -> {
					System.out.println("DEBUG:: Record found for id=" + id + " to be updted.");
					// update
					person.setId(id);
					person.setRoot(member.isRoot());
					person.setFirstName(member.getFirstName());
					person.setLastName(member.getLastName());
					person.setGender(member.getGender().toString());
					
					int year = getYear(member.getDateOfBirth());
					int month = getMonth(member.getDateOfBirth()) == 0 ? 1 : getMonth(member.getDateOfBirth());
					int dayOfMonth = getDayOfMonth(member.getDateOfBirth()) == 0 ? 1 : getDayOfMonth(member.getDateOfBirth());
					person.setDateOfBirth(LocalDate.of(year, month, dayOfMonth));
					
					year = getYear(member.getDateOfDeah());
					month = getMonth(member.getDateOfDeah()) == 0 ? 1 : getMonth(member.getDateOfDeah());
					dayOfMonth = getDayOfMonth(member.getDateOfDeah()) == 0 ? 1 : getDayOfMonth(member.getDateOfDeah());
					person.setDateOfDeath(LocalDate.of(year, month, dayOfMonth));
					
					person.setMarried(member.isMarried());
					person.setFather(Integer.parseInt(member.getFatherId() == null || member.getFatherId() == "" ? "-1" : member.getFatherId()));
					person.setMother(Integer.parseInt(member.getMotherId() == null || member.getMotherId() == "" ? "-1" : member.getMotherId()));
					person.setSpouse(coallateIntoOneStr(member.getSpouseIds()));
					person.setChildren(coallateIntoOneStr(member.getChildrenIds()));
					
					personRepository.save(person);
					System.out.println("INFO:: Record with id=" + id + " updated successfully");
				},
				() -> {
					// null value
					System.out.println("ERROR:: Null value - no record found for id=" + id);
				});
		
	}

}
