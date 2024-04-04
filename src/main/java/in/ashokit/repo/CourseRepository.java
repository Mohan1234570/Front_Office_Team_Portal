package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer>{

}
