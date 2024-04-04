package in.ashokit.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="STUDENT_ENQUIRY_DTLS")
public class StudentEnqEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Long studentPhNo;
	private String classMode;
	private String  courseName;
	private String enquiryStatus;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;

	@ManyToOne
	@JoinColumn(name = "userId")
	private  UserDetailsEntity user;
}
