package es.dispenser.service;

import java.util.UUID;

import es.dispenser.dto.ChangeDispenserDto;

public interface DraftService {
	
	void openCloseTap(UUID id, ChangeDispenserDto changeDisp)throws Exception;	
}
