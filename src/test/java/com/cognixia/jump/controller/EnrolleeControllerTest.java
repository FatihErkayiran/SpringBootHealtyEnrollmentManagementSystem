package com.cognixia.jump.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Dependents;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.repository.EnrolleeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EnrolleeController.class)
class EnrolleeControllerTest {
	
	private final String STARTING_URI="http://localhost:8080/api";
	
	EnrolleeController controller;
	
	@MockBean
	private EnrolleeRepository repo;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAllEnrollees() throws Exception{
		String uri=STARTING_URI + "/enrollees";
		
		List<Enrollee> allEnrollees =Arrays.asList(
				new Enrollee(7L, "Mike", false, LocalDate.of(1980, 10, 10), "2253435656", new ArrayList<>()),
				new Enrollee(8L, "Glenn", false, LocalDate.of(1990, 12, 10), "2253430656", new ArrayList<>())
				);
		when( repo.findAll() ).thenReturn(allEnrollees);
		mockMvc.perform(get(uri))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.length()").value(allEnrollees.size()))
	       .andExpect(jsonPath("$[0].name").value(allEnrollees.get(0).getName()))
	       .andExpect(jsonPath("$[1].telephoneNumber").value(allEnrollees.get(1).getTelephoneNumber()));
	
	
	verify(repo,times(1)).findAll();
	verifyNoMoreInteractions(repo);
		
	}
	
	@Test
	void testGetOneEnrollee() throws Exception{
		String uri=STARTING_URI+"/enrollee/{id}";
		long id=1;
		
		Enrollee enrollee=new Enrollee(7L, "Mike", false, LocalDate.of(1980, 10, 10), "2253435656", new ArrayList<>());
		
		when(repo.findById(id)).thenReturn(Optional.of(enrollee));
		
		mockMvc.perform(get(uri,id))
	       .andExpect(status().isOk())
	       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	       .andExpect(jsonPath("$.name").value(enrollee.getName()))
	       .andExpect(jsonPath("$.telephoneNumber").value(enrollee.getTelephoneNumber()));
	       
	verify(repo,times(1)).findById(id);
	verifyNoMoreInteractions(repo);
	}
	
	@Test
	void testEnrolleeNotFoundById()throws Exception {
		
		String uri=STARTING_URI + "/enrollee/{id}";
		long id=1;
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		mockMvc.perform(get(uri,id))
	       			.andExpect(status().isNotFound());
		
		verify(repo,times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}
	
	
	@Test
	void testAddEnrollee() throws Exception{
		
		String uri=STARTING_URI+"/add/enrollee";
		
		Enrollee enroll = new Enrollee(2L, "Sarah", false, LocalDate.of(1990, 6, 12), "2252435656", new ArrayList<Dependents>());
	    
		when(repo.save(Mockito.any(Enrollee.class))).thenReturn(enroll);
		mockMvc.perform(post(uri)
		        .contentType( MediaType.APPLICATION_JSON)
		        .content( asJsonString(enroll)))
		.andDo(print())
	//	.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name").value(enroll.getName()));
		
		verify(repo,times(1)).save(Mockito.any(Enrollee.class));
		verifyNoMoreInteractions(repo);
		        
	
	}
	
	@Test
	void testUpdateEnrollee()throws Exception{
		String uri=STARTING_URI+"/update/enrollee";
		long id=1L;
		
		Enrollee enrollee=new Enrollee(7L, "Mike", false, LocalDate.of(1980, 10, 10), "2253435656", new ArrayList<>());
		 when(repo.existsById(id)).thenReturn(true);
		  when(repo.save(Mockito.any(Enrollee.class))).thenReturn(enrollee);
		  
		  mockMvc.perform(put(uri)
		          .contentType(MediaType.APPLICATION_JSON)
		          .content(asJsonString(enrollee)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.id").value(enrollee.getId()));
  
  verify(repo,times(1)).existsById(id);
  verify(repo,times(1)).save(Mockito.any(Enrollee.class));
  verifyNoMoreInteractions(repo);
	}
	
	@Test
	void testDeleteEnrollee()throws Exception{
		String uri=STARTING_URI+"/delete/enrollee/{id}";
		long id=1L;
		
		Enrollee enrollee4=new Enrollee(9L, "Kyle", true, LocalDate.of(2000, 9, 8), "3321231313", new ArrayList<>());
	
		when(repo.existsById(id)).thenReturn(true);
		when(repo.findById(id)).thenReturn(Optional.of(enrollee4));
		doNothing().when(repo).deleteById(id);
		
		mockMvc.perform( delete(uri,id)
		          .contentType( MediaType.APPLICATION_JSON)
		          .content( asJsonString(enrollee4)))
	   .andDo(print())
       .andExpect( status().isOk());
       

		verify(repo,times(1)).deleteById(id);
	     
	
	
	
	}
	

	
	
	
	
	public static String asJsonString(final Object obj) {
		
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch(Exception e) {
			throw new RuntimeException();
		}
		
	}
	
}
