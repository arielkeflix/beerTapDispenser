package es.dispenser.service;


import java.util.List;

import es.dispenser.domain.Dispenser;
import es.dispenser.dto.ChangeDispenserDto;
import es.dispenser.dto.UsageDto;

public interface DraftService {
	
	void createDraft(Dispenser dispenser, ChangeDispenserDto changeDisp)throws Exception;
	List<UsageDto> spendingByDispenser(Dispenser dispenser);
	Double calculateAmount(List<UsageDto> usages);

}
