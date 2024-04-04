package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.DashboardForm;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.CourseRepository;
import in.ashokit.repo.EnqStatusRepository;
import in.ashokit.repo.StudentEnqRepository;
import in.ashokit.repo.UserDetailsRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService{

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private StudentEnqRepository stuEnqRepository;

	@Autowired
	private CourseRepository  courseRepository;

	@Autowired
	private EnqStatusRepository enqStatusRepository;

	@Autowired
	private HttpSession session;

	@Override
	public List<String> getCourses() {
		// TODO Auto-generated method stub
		List<CourseEntity> findAll = courseRepository.findAll();
		List<String> names = new ArrayList<>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnquiryStatus() {
		// TODO Auto-generated method stub
		List<EnqStatusEntity> findAll = enqStatusRepository.findAll();
		List<String> statusList = new ArrayList<>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	@Override
	public DashboardForm getDashBoardData(Integer userId) {
		// TODO Auto-generated method stub

		DashboardForm response = new DashboardForm();

		Optional<UserDetailsEntity> findById= userRepository.findById(userId);

		if (findById.isPresent()) {

			UserDetailsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getStudentEnqDetails();
			Integer totalCnt = enquiries.size();
			Integer enrollCnt = enquiries.stream()
					.filter(e -> e.getEnquiryStatus().equals("ENROLLED"))
					.collect(Collectors.toList()).size();
			Integer lostCnt = enquiries.stream()
					.filter(e -> e.getEnquiryStatus().equals("LOST"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiriesCount(totalCnt);
			response.setTotalEnrolledCount(enrollCnt);
			response.setTotalLastCount(lostCnt);
		}
		return response;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm enquiryForm) {
		// TODO Auto-generated method stub
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, enqEntity);

		Integer userId=(Integer)session.getAttribute("userId");

		UserDetailsEntity userDetailsEntity= userRepository.findById(userId).get();

		enqEntity.setUser(userDetailsEntity);

		stuEnqRepository.save(enqEntity);

		return true;
	}

	@Override
	public List<EnquiryForm> viewEnqieries(Integer userId, EnquirySearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		// TODO Auto-generated method stub
		Integer userId =(Integer) session.getAttribute("userId");
		Optional<UserDetailsEntity> findById= userRepository.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity entity=findById.get();
		    List<StudentEnqEntity> enquiries= entity.getStudentEnqDetails();
		    return enquiries;

		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria, Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserDetailsEntity> findById= userRepository.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity entity=findById.get();
		    List<StudentEnqEntity> enquiries= entity.getStudentEnqDetails();
		    
		    //filter logic
		    
		    if (null != criteria.getCourseName() & !"".equals(criteria.getCourseName())) {
		    	enquiries=	enquiries.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName())).collect(Collectors.toList());
			}
		    if (null != criteria.getEnqStatus() & !"".equals(criteria.getEnqStatus())) {
		    	enquiries=	enquiries.stream()
				.filter(e -> e.getEnquiryStatus().equals(criteria.getEnqStatus())).collect(Collectors.toList());
			}
		    if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
		    	enquiries=	enquiries.stream()
				.filter(e -> e.getClassMode().equals(criteria.getClassMode())).collect(Collectors.toList());
			}
		    return enquiries;
		}
		return null;
	}

}
