package in.ashokit.service;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.UserDetailsRepository;
import in.ashokit.utility.EmailUtils;
import in.ashokit.utility.PassowrdUtils;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository repo;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm form) {
		// TODO Auto-generated method stub
		
		UserDetailsEntity entity= repo.findByEmailAndPassword(form.getEmail(), form.getPwd());
		if (entity == null) {
			return "Invalid credintials";
		}
		if (entity.getAccountStatus().equals("LOCKED")) {
			return "your account locked";
		}
		
		//create session and store the user data in session
		session.setAttribute("userId", entity.getUserId());
		
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm form) {

		boolean isSent = false;
		try {
			UserDetailsEntity user = repo.findByEmail(form.getEmail());
			
			if (user != null) {
				return false;
			}
			
			//TODO: Generate a ramdom password
			String tempPwd= PassowrdUtils.randomPwdGenerator();

			//TODO:Copy the properties from  signUpform to entity
			UserDetailsEntity entity = new UserDetailsEntity();
			BeanUtils.copyProperties(form, entity);

			//TODO: set the pwd to entity object
			entity.setPassword(tempPwd);

			//TODO: set the status as LOCKED
			entity.setAccountStatus("LOCKED");

			String to = "archanagajula1305@gmail.com";
			String subject ="Please Check your Password For";

			//String body ="<a href= \" http://localhost:8080/unlock?email= "+ to+" \" >Click here to change the password</a> ";

			StringBuffer body = new StringBuffer();
			body.append("<h1>Bellow temporary password to unlock your account</h1>");
			body.append("Temporary password:: "+tempPwd);
			body.append("<br/>");
			body.append("<a href= \" http://localhost:8080/unlock?email="+ form.getEmail()+" \" >Click here to change the password</a> ");
			
			//TODO: Save the record in the table 
			repo.save(entity);

			//send email to unlock the account
			emailUtils.sendEmail(to, subject, body.toString());
			isSent = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return isSent;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		// TODO Auto-generated method stub
		UserDetailsEntity entity =  repo.findByEmail(form.getEmail());
		
		if (entity.getPassword().equals(form.getTempPwd())) {
			
			entity.setPassword(form.getNewPwd());
			entity.setAccountStatus("UnLocked");
			repo.save(entity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String email) {
		// TODO Auto-generated method stub
		
		UserDetailsEntity entity = repo.findByEmail(email);
		
		if (entity == null) {
			return false;
		}
		
		String subject="Recover Password";
		String body="your Pwd :: "+entity.getPassword();
		
		emailUtils.sendEmail(email, subject, body);
		
		return true;
	}

}
