package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StudentEnqEntity;

public interface StudentEnqRepository  extends JpaRepository<StudentEnqEntity, Integer> {

}
