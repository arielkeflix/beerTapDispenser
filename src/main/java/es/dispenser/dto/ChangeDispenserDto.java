package es.dispenser.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ChangeDispenserDto {
	private String status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

	private LocalDateTime updatedAt;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}	
}
