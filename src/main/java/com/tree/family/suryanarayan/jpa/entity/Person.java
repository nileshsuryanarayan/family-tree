package com.tree.family.suryanarayan.jpa.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "person")
@DynamicInsert
@DynamicUpdate
public class Person implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 9068144437925099810L;

	@Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_root", nullable = false)
    private boolean isRoot = false;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(length = 50)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;

    @Column(name = "is_married", nullable = false)
    private boolean isMarried = false;

    private Integer father;
    private Integer mother;

    @Column(length = 255)
    private String spouse;

    @Column(length = 255)
    private String children;

	
}
