package com.tree.family.suryanarayan.jpa.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tree.family.suryanarayan.jpa.entity.Person;

import jakarta.transaction.Transactional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

	@Modifying
    @Transactional
	@Query(value = "TRUNCATE TABLE FAMILY_TREE.PERSON", nativeQuery = true)
	public void truncateTable();
	
}
