package es.dispenser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import es.dispenser.domain.Dispenser;
import es.dispenser.repository.IDispenserRepository;
import es.dispenser.service.DispenserServiceImpl;

@ExtendWith(MockitoExtension.class)

class DispenserServiceTest {
	
	@InjectMocks
	private DispenserServiceImpl out;
	
	@Mock
	IDispenserRepository dispenserRepository;

	@Test
	@DisplayName("Find one  input invalid ID -> exception")
	void findOneDispenseThrowExceptionTest() {		
		UUID id = UUID.randomUUID();
		Optional<Dispenser> dispenserOpt = Optional.empty(); 

		when( dispenserRepository.findById(id)).thenReturn(dispenserOpt);
		
		Throwable exception = assertThrows(Exception.class, ()-> out.findOneDispenser(id));
		assertEquals("There is no Dispenser with Id: "+id, exception.getMessage());				
	}
	@Test
	@DisplayName("Find one with id right")
	void findOneDispenseRightIdTest() throws Exception {		
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();
		dispenser.setFlowVolume((Double) 0.123);
		Optional<Dispenser> dispenserOpt = Optional.of(dispenser); 

		when( dispenserRepository.findById(id)).thenReturn(dispenserOpt);

		assertEquals((Double)0.123,  out.findOneDispenser(id).getFlowVolume() );				
	}
	@Test
	@DisplayName("Save with id right")
	void saveDispenserRightIdTest() throws Exception {	
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();	
		dispenser.setDispenserId(id);
		
		when( dispenserRepository.save(dispenser)).thenReturn(dispenser);

		assertEquals(dispenser.getDispenserId(),  out.saveDispenser(dispenser) );				
	}

}
