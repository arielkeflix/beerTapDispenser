package es.dispenser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.dispenser.domain.Dispenser;
import es.dispenser.domain.Draft;
import es.dispenser.dto.ChangeDispenserDto;
import es.dispenser.repository.IDraftRepository;

@ExtendWith(MockitoExtension.class)
class DraftServiceImplTest {
	private static final String CLOSE = "close";
	private static final String OPEN = "open";
	private static final Double LITER_PRICE = 12.25; 

	@InjectMocks
	private DraftServiceImpl out;

	@Mock
	IDraftRepository draftRepository;
	@Mock
	DispenserService dispenserService;

	@Test
	@DisplayName("open/close tap with input invalid status -> exception")
	void openCloseTapTypoErrorStatusTest() {
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();		
		ChangeDispenserDto changeDisp = new ChangeDispenserDto();
		changeDisp.setStatus("other");
		
		Throwable exception = assertThrows(Exception.class, ()-> out.openCloseTap(id,changeDisp));		
		assertEquals("The tap dispenser : accept only '"+ OPEN + "' and '" + CLOSE + "' NOT: '"+  changeDisp.getStatus()+"'", exception.getMessage());	
	}
	@Test
	@DisplayName("open/close tap with input  status the same that it has -> exception")
	void openCloseTapSameStatusTest() throws Exception {
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();	
		dispenser.setStatus(OPEN);
		ChangeDispenserDto changeDisp = new ChangeDispenserDto();
		changeDisp.setStatus(OPEN);
		
		when( dispenserService.findOneDispenser(id)).thenReturn(dispenser);
		
		Throwable exception = assertThrows(Exception.class, ()-> out.openCloseTap(id ,changeDisp));		
		assertEquals("the dispenser with id: "+dispenser.getDispenserId()+ " is already "+  dispenser.getStatus(), exception.getMessage());	
	}
	@Test
	@DisplayName("open/close tap try to close before open -> exception")
	void openCloseTapTryCloseBeforeOpen() throws Exception {
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();		
		dispenser.setStatus("");
		ChangeDispenserDto changeDisp = new ChangeDispenserDto();
		changeDisp.setStatus(CLOSE);
		
		when( dispenserService.findOneDispenser(id)).thenReturn(dispenser);
		
		Throwable exception = assertThrows(Exception.class, ()-> out.openCloseTap( id,changeDisp));		
		assertEquals("You can't close the dispenser with id: "+dispenser.getDispenserId()+ " because is NOT "+  OPEN, exception.getMessage());	
	}
	@Test
	@DisplayName("open/close tap with input  'open' right-> no exception")
	void openCloseTapOpenRightTest()throws Exception {
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();			
		ChangeDispenserDto changeDisp = new ChangeDispenserDto();
		changeDisp.setStatus(OPEN);		

		when( dispenserService.findOneDispenser(id)).thenReturn(dispenser);
		
		assertDoesNotThrow(() -> out.openCloseTap(id, changeDisp));

	}
	@Test
	@DisplayName("open/close tap with input  'close' right-> no exception")
	void openCloseTapCloseRightTest()throws Exception {
		UUID id = UUID.randomUUID();
		Dispenser dispenser = new Dispenser();	
		dispenser.setStatus(OPEN);
		ChangeDispenserDto changeDisp = new ChangeDispenserDto();
		changeDisp.setStatus(CLOSE);
		
		when( dispenserService.findOneDispenser(id)).thenReturn(dispenser);

		assertDoesNotThrow(() -> out.openCloseTap(id, changeDisp));

	}
}
