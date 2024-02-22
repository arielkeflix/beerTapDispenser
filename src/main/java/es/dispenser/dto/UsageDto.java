package es.dispenser.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsageDto {
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	private LocalDateTime openedAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	private LocalDateTime closedAt;
	private Double totalSpend;
	private Float flowVolume;
	
	public LocalDateTime getOpenedAt() {
		return openedAt;
	}
	public void setOpenedAt(LocalDateTime openedAt) {
		this.openedAt = openedAt;
	}
	public LocalDateTime getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}
	public Double getTotalSpend() {
		return totalSpend;
	}
	public void setTotalSpend(Double totalSpend) {
		this.totalSpend = totalSpend;
	}
	public Float getFlowVolume() {
		return flowVolume;
	}
	public void setFlowVolume(Float flowVolume) {
		this.flowVolume = flowVolume;
	}		
}
