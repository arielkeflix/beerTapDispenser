package es.dispenser.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.dispenser.domain.Dispenser;
import es.dispenser.dto.ChangeDispenserDto;
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
	public Map<String, Object> createDispenser(@RequestBody Dispenser dispenser){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {							
			map.put("Dispenser Id ", dispenserService.saveDispenser(dispenser));
			map.put("Dispenser flow_volume ", dispenser.getFlowVolume());
			map.put("success", "true");
			map.put("message", "Ok");				
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}		
		return map;
	}
	
	@PutMapping ("/{id}")
	public Map<String, Object> changeDispenser(@PathVariable(name = "id") UUID id, @RequestBody ChangeDispenserDto changeDisp){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {					
			Dispenser dispenser = dispenserService.findOneDispenser(id);
			draftService.createDraft (dispenser, changeDisp);
			dispenserService.saveDispenser(dispenser);
			map.put("Dispenser  ",dispenser);			
			map.put("success", "true");
			map.put("message", "Ok");			
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}		
		return map;
	}
	
	@GetMapping ("/{id}/spending")
	public Map<String, Object> spendingDispenser(@PathVariable(name = "id") UUID id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List <UsageDto> usages = new ArrayList<UsageDto>();

		try {					
			usages = draftService.spendingByDispenser(dispenserService.findOneDispenser(id));
			map.put("Amount",  draftService.calculateAmount(usages));
			map.put("Usages  ",usages);			
			map.put("success", "true");
			map.put("message", "Ok");		
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}	
		return map;
	}
}
