package es.dispenser.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.dispenser.dto.ChangeDispenserDto;
import es.dispenser.dto.DispenserDto;
import es.dispenser.dto.UsageDto;
import es.dispenser.service.DispenserService;
import es.dispenser.service.DraftService;

@RestController
@RequestMapping("dispenser/")
public class DispenserController {
	
	@Autowired
	DispenserService dispenserService;
	@Autowired
	DraftService draftService ;		

	@PostMapping 
	public ResponseEntity<?> createDispenser(@RequestBody DispenserDto dispFlowVolume ){		
		
		try {
			DispenserDto dispenserDto = dispenserService.createDispenser(dispFlowVolume.getFlowVolume());						
			return ResponseEntity.status(HttpStatus.OK).body(dispenserDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}			 
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<?> changeDispenser(@PathVariable(name = "id") UUID id, @RequestBody ChangeDispenserDto changeDisp){		
		
		try {								
			draftService.openCloseTap(id, changeDisp);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();					
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}				
	}
	
	@GetMapping ("/{id}/spending")
	public ResponseEntity<?>spendingDispenser(@PathVariable(name = "id") UUID id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List <UsageDto> usages = new ArrayList<UsageDto>();

		try {					
			usages = dispenserService.spendingByDispenser(id);
			map.put("Amount",  dispenserService.calculateAmount(usages));
			map.put("Usages",usages);			
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}		
	}
}
