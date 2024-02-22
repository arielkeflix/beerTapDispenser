package es.dispenser.service;

import java.util.UUID;

import es.dispenser.domain.Dispenser;

public interface DispenserService {
	public  UUID saveDispenser(Dispenser dispenser);
	public Dispenser findOneDispenser(UUID dispenserId)throws Exception;

}
