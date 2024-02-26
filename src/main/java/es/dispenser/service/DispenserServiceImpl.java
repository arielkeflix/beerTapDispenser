package es.dispenser.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dispenser.domain.Dispenser;
import es.dispenser.domain.Draft;
import es.dispenser.dto.DispenserDto;
import es.dispenser.dto.UsageDto;
import es.dispenser.repository.IDispenserRepository;

@Service
public class DispenserServiceImpl implements DispenserService {
	private static final Double LITER_PRICE = 12.25;
	@Autowired
	IDispenserRepository repository;
	
	@Override
	public DispenserDto createDispenser(Double flowVolume) {	
		Dispenser dispenser = new Dispenser(flowVolume);
		Dispenser dispenserResult =	repository.save(dispenser);				
		return dispenserToDispenserDto(dispenserResult);
	}
	@Override
	public UUID saveDispenser(Dispenser dispenser) {		
		Dispenser dispenserResult =	repository.save(dispenser);				
		return dispenserResult.getDispenserId();
	}

	
	@Override
	public List<UsageDto> spendingByDispenser(UUID id)throws Exception {		
		Dispenser dispenser = findOneDispenser(id);
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
	public Dispenser findOneDispenser(UUID dispenserId) throws Exception {
		Optional<Dispenser> dispenserOpt =	repository.findById(dispenserId);
		if (dispenserOpt.isEmpty())
			throw new Exception("There is no Dispenser with Id: "+dispenserId );
		return dispenserOpt.get();		
	}
	@Override
	public Double calculateAmount(List<UsageDto> usages) {
		Double amount =0.0;
		for (UsageDto u : usages) 			
			amount = amount + u.getTotalSpend();

		return amount;
	}
	private Double calculateTotalSpend(Double flowVolume, Draft d) {
		Long closeTap ;
		Long openTap = d.getOpenedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		
		if (d.getClosedAt()==null) closeTap = System.currentTimeMillis();		
		else 
			closeTap = d.getClosedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();					

		Long openTime =(closeTap - openTap)/1000;
		BigDecimal beerSpend = new BigDecimal(flowVolume*openTime*LITER_PRICE).setScale(4, RoundingMode.DOWN);
		return beerSpend.doubleValue();		
	}
	public DispenserDto dispenserToDispenserDto(Dispenser dispenser) {
		
		DispenserDto dispenserDto = new DispenserDto();
		dispenserDto.setDispenserId(dispenser.getDispenserId());
		dispenserDto.setFlowVolume(dispenser.getFlowVolume());
		
		return dispenserDto;
	}
}
