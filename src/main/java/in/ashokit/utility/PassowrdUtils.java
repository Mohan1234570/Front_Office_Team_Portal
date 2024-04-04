package in.ashokit.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PassowrdUtils {
	
	public static String randomPwdGenerator() {
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String tempPwd = RandomStringUtils.random( 6, characters );
		
		return tempPwd;
	}
}
