package es.dispenser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name ="dispenser")
public class Dispenser {

	@Id  
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "UUID")
	@Column(name = "dispenser_id")
	private UUID dispenserId;	

	@OneToMany(mappedBy = "dispenser")
	private List<Draft> drafts=new ArrayList<Draft>();		

	@Column(name = "flow_Volume") 
	private Double flowVolume; 

	private String status;

	public Dispenser() {
		super();
	}
	public Dispenser(Double flowVolume) {
		this.flowVolume = flowVolume;
	}
	public List<Draft> getDrafts() {
		return drafts;
	}
	public void addDrafts(Draft draft) {
		drafts.add(draft);
	}		
	public Double getFlowVolume() {
		return flowVolume;
	}
	public void setFlowVolume(Double flowVolume) {
		this.flowVolume = flowVolume;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UUID getDispenserId() {
		return dispenserId;
	}
	public void setDispenserId(UUID dispenserId) {
		this.dispenserId = dispenserId;
	}
	
}
