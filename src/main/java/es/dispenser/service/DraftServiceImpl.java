package es.dispenser.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	private static final Double LITER_PRICE = 12.25;
	
	@Autowired
	IDraftRepository repository;
	
	@Override
	public void createDraft(Dispenser dispenser, ChangeDispenserDto changeDisp)throws Exception {
		validateOpenCloseTap(dispenser, changeDisp);		
		dispenser.setStatus(changeDisp.getStatus());
		if ( changeDisp.getStatus().equals(OPEN))
			openDraft(dispenser, changeDisp);
		else closeDraft(dispenser, changeDisp);
	}

	private void validateOpenCloseTap(Dispenser dispenser, ChangeDispenserDto changeDisp) throws Exception {
		if ( ! Set.of(OPEN,CLOSE).contains(changeDisp.getStatus()) )  
			throw new Exception("The tap dispenser : accept only '"+ OPEN + "' and '" + CLOSE + "' NOT: '"+  changeDisp.getStatus()+"'");
		if (changeDisp.getStatus().equals(dispenser.getStatus()))
			throw new Exception("the dispenser id: "+dispenser.getDispenserId()+ " is almost "+  dispenser.getStatus());
		if (changeDisp.getStatus().equals(CLOSE) && !OPEN.equals(dispenser.getStatus()))
			throw new Exception("You can't close the dispenser id: "+dispenser.getDispenserId()+ " because is NOT "+  OPEN);
		
	}

	private void closeDraft(Dispenser dispenser, ChangeDispenserDto changeDisp) {
		for (Draft d : dispenser.getDrafts()) {
			if ( d.getClosedAt()==null) {
				d.setClosedAt(changeDisp.getUpdatedAt());				
			}			
		}
	}

	private Double calculateTotalSpend(Float flowVolume, Draft d) {
		Long closeTap ;
		Long openTap = d.getOpenedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		
		if (d.getClosedAt()==null) closeTap = System.currentTimeMillis();		
		else 
			closeTap = d.getClosedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();					

		Long openTime =(closeTap - openTap)/1000;
		BigDecimal beerSpend = new BigDecimal(flowVolume*openTime*LITER_PRICE).setScale(4, RoundingMode.DOWN);
		return beerSpend.doubleValue();		
	}

	private void openDraft(Dispenser dispenser, ChangeDispenserDto changeDisp) {
		
		Draft draft = new Draft(changeDisp.getUpdatedAt(),dispenser);
		repository.save(draft);
		dispenser.addDrafts(draft);
	}

	@Override
	public List<UsageDto> spendingByDispenser(Dispenser dispenser) {
		 List<UsageDto> usages = new ArrayList<UsageDto>();
		 for (Draft d : dispenser.getDrafts()) {
			 UsageDto usageDto = new UsageDto();
			 usageDto.setFlowVolume(dispenser.getFlowVolume());
			 usageDto.setOpenedAt(d.getOpenedAt());
			 usageDto.setClosedAt(d.getClosedAt());
			 usageDto.setTotalSpend( calculateTotalSpend(dispenser.getFlowVolume(),d) );
			 usages.add(usageDto);
		 }
		return usages;
	}

	@Override
	public Double calculateAmount(List<UsageDto> usages) {
		Double amount =0.0;
		for (UsageDto u : usages) 			
			amount = amount + u.getTotalSpend();

		return amount;
	}



}
