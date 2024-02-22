package es.dispenser.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dispenser.domain.Dispenser;
import es.dispenser.repository.IDispenserRepository;

@Service
public class DispenserServiceImpl implements DispenserService {
	@Autowired
	IDispenserRepository repository;
	
	@Override
	public UUID saveDispenser(Dispenser dispenser) {		
		Dispenser dispenserResult =	repository.save(dispenser);				
		return dispenserResult.getDispenserId();
	}

	@Override
	public Dispenser findOneDispenser(UUID dispenserId) throws Exception {
		Optional<Dispenser> dispenserOpt =	repository.findById(dispenserId);
		if (dispenserOpt.isEmpty())
			throw new Exception("Doesn't exist a Dispenser with Id: "+dispenserId );
		return dispenserOpt.get();		
	}
   int calculate(int a, int b) {
	   return a+b;
   }
}
