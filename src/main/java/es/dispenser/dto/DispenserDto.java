package es.dispenser.dto;

import java.util.UUID;

import es.dispenser.domain.Dispenser;

public class DispenserDto {
	private Double flowVolume; 
	private UUID dispenserId;
	
	public Double getFlowVolume() {
		return flowVolume;
	}

	public void setFlowVolume(Double flowVolume) {
		this.flowVolume = flowVolume;
	}

	public UUID getDispenserId() {
		return dispenserId;
	}

	public void setDispenserId(UUID dispenserId) {
		this.dispenserId = dispenserId;
	}

	
}
