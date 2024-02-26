package es.dispenser.service;

import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dispenser.domain.Dispenser;
import es.dispenser.domain.Draft;
import es.dispenser.dto.ChangeDispenserDto;
import es.dispenser.dto.UsageDto;
import es.dispenser.repository.IDraftRepository;

@Service
public class DraftServiceImpl implements DraftService {
	private static final String CLOSE = "close";
	private static final String OPEN = "open";
	
	@Autowired
	DispenserService dispenserService;
	@Autowired
	IDraftRepository repository;
	
	@Override
	public void openCloseTap(UUID id, ChangeDispenserDto changeDisp)throws Exception {
		Dispenser dispenser = dispenserService.findOneDispenser(id);
		validateOpenCloseTap(dispenser, changeDisp);		
		dispenser.setStatus(changeDisp.getStatus());
		if ( changeDisp.getStatus().equals(OPEN))
			openDraft(dispenser, changeDisp);
		else closeDraft(dispenser, changeDisp);
		dispenserService.saveDispenser(dispenser);
	}

	private void validateOpenCloseTap(Dispenser dispenser, ChangeDispenserDto changeDisp) throws Exception {
		if ( ! Set.of(OPEN,CLOSE).contains(changeDisp.getStatus()) )  
			throw new Exception("The tap dispenser : accept only '"+ OPEN + "' and '" + CLOSE + "' NOT: '"+  changeDisp.getStatus()+"'");
		if (changeDisp.getStatus().equals(dispenser.getStatus()))
			throw new Exception("the dispenser with id: "+dispenser.getDispenserId()+ " is already "+  dispenser.getStatus());
		if (changeDisp.getStatus().equals(CLOSE) && !OPEN.equals(dispenser.getStatus()))
			throw new Exception("You can't close the dispenser with id: "+dispenser.getDispenserId()+ " because is NOT "+  OPEN);
		
	}

	private void closeDraft(Dispenser dispenser, ChangeDispenserDto changeDisp) {
		for (Draft d : dispenser.getDrafts()) {
			if ( d.getClosedAt()==null) {
				d.setClosedAt(changeDisp.getUpdatedAt());				
			}			
		}
	}
	private void openDraft(Dispenser dispenser, ChangeDispenserDto changeDisp) {
		
		Draft draft = new Draft(changeDisp.getUpdatedAt(),dispenser);
		repository.save(draft);
		dispenser.addDrafts(draft);
	}
}
