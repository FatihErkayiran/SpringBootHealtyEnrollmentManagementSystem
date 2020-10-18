package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognixia.jump.model.Dependents;



public interface DependentsRepository extends JpaRepository<Dependents, Long>{
	List<Dependents>findByEnrolleeId(long id);
	Optional<Dependents>findByIdAndEnrolleeId(long id,long enrolleeId);

}
