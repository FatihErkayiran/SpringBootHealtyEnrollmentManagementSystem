package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Dependents;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.repository.DependentsRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Dependents.class)
class DependentsControllerTest {
	
	private final String STARTING_URI="http://localhost:8080/api";
	
	DependentsController controller;
	
	@MockBean
	private DependentsRepository repo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testDependentById() throws Exception{
		String uri=STARTING_URI + "/dependents/{id}";
		long id =1;
		
		Dependents dependents =new Dependents(1L, "Sami", LocalDate.of(2000, 10, 19));
		
		when(repo.findById(id)).thenReturn(Optional.of(dependents));
		
		mockMvc.perform(get(uri,id))
	       .andExpect(status().isOk())
	       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	       .andExpect(jsonPath("$.id").value(dependents.getId()));
		
		verify(repo,times(1)).findById(id);
		verifyNoMoreInteractions(repo);
		
		
	}
	
	@Test
	void testGetDependentsByEnrolleeId() throws Exception{
		String uri=STARTING_URI + "/enrollee/{enrolleId}/dependents";
		long id=1;
		
		//Enrollee enrollee=new Enrollee(id, "kyle", false, LocalDate.of(year, month, dayOfMonth), telephoneNumber, dependents)
		
		
	}
	

	

}
