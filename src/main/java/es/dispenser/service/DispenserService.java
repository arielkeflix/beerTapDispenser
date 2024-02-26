package es.dispenser.service;

import java.util.List;
import java.util.UUID;

import es.dispenser.domain.Dispenser;
import es.dispenser.dto.DispenserDto;
import es.dispenser.dto.UsageDto;

public interface DispenserService {
	public  DispenserDto createDispenser(Double flowVolume);
	public Dispenser findOneDispenser(UUID dispenserId)throws Exception;
	List<UsageDto> spendingByDispenser(UUID id)throws Exception;
	Double calculateAmount(List<UsageDto> usages);
	UUID saveDispenser(Dispenser dispenser);
}
