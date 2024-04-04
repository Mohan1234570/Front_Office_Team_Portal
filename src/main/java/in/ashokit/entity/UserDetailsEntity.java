package in.ashokit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="ALL_USER_DTLS")
public class UserDetailsEntity {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer userId;
	 private String name;
	 private String email;
	 private Long phNo;
	 private String password;
	 private String accountStatus;
	 
	 @OneToMany(cascade = CascadeType.ALL,  mappedBy = "user", fetch = FetchType.EAGER )
	 private List<StudentEnqEntity>  studentEnqDetails;
}
