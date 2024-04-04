package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="ENQUIRY_STATUS")
public class EnqStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;
}
