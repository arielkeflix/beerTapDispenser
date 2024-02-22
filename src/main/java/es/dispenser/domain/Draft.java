package es.dispenser.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name ="draft")
public class Draft {
	
	@Id  
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "UUID")
	@Column(name = "id")
	private UUID id;	
	
	@Nonnull
	@Column(name = "opened_at") 
	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	private LocalDateTime openedAt;
	
	@Column(name = "closed_at") 
	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	private LocalDateTime closedAt;

	@ManyToOne
	@JoinColumn(name = "dispenserId")
	private Dispenser dispenser;
	
	public Draft() {
		super();		
	}
	public Draft(LocalDateTime openedAt, Dispenser dispenser) {
		super();
		this.openedAt = openedAt;
		this.dispenser = dispenser;
	}
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


	
}
