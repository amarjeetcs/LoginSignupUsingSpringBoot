package com.backend.registration.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    private final Map<String, String> otpStorage = new HashMap<>();

    public String generateOtp(String phone) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(phone, otp);
        // Simulate sending OTP (in real app, integrate SMS API like Twilio)
        System.out.println("OTP for " + phone + " is: " + otp);
        return otp;
    }

    public boolean verifyOtp(String phone, String otp) {
        return otpStorage.containsKey(phone) && otpStorage.get(phone).equals(otp);
    }

    public void clearOtp(String phone) {
        otpStorage.remove(phone);
    }
}
