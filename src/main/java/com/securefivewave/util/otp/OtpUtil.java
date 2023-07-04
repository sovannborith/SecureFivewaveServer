package com.securefivewave.util.otp;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class OtpUtil {

    public String generateOtp() {
    Random random = new Random();
    int randomNumber = random.nextInt(999999);
    String output = Integer.toString(randomNumber);

        output = "0" + output;    
    return output;
    }
}