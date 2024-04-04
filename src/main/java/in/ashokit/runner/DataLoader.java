package in.ashokit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.repo.CourseRepository;
import in.ashokit.repo.EnqStatusRepository;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private EnqStatusRepository statusRepository;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		courseRepository.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("JAVA");
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("PYTHON");
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("DEVOPS");
		
		courseRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		statusRepository.deleteAll();
		
		EnqStatusEntity s1= new EnqStatusEntity();
		s1.setStatusName("NEW");
		
		EnqStatusEntity s2= new EnqStatusEntity();
		s2.setStatusName("ENROLLED");
		EnqStatusEntity s3= new EnqStatusEntity();
		s3.setStatusName("LOST");
		
		statusRepository.saveAll(Arrays.asList(s1,s2,s3));
	}

}
