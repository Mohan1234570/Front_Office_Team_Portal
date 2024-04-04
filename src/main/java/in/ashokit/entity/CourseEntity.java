package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="COURSE_DTLS")
public class CourseEntity {
	
	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;

}
