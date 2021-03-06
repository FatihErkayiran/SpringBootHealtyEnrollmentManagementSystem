package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Enrollee;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
	@Query("select u from Enrollee u where u.birthDate < 1960-01-01")
	List<Enrollee> getSeniorEnrollees();

}
