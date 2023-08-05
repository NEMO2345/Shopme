package com.shopme;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.shopme.setting.EmailSettingBag;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {

	public static String getSiteURL(HttpServletRequest request) {
		
		String siteURL = request.getRequestURI().toString();
		
		return siteURL.replace(request.getServletPath(),"");
		
	}
	
	public static JavaMailSenderImpl prepareJavaMailSender(EmailSettingBag settings) {
			
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
			mailSender.setHost(settings.getHost());
			mailSender.setPort(settings.getPort());
			mailSender.setUsername(settings.getUsername());
			mailSender.setPassword(settings.getPassword());
			
			Properties mailProperties = new Properties();
			mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
			mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());

			mailSender.setJavaMailProperties(mailProperties);
			
			return mailSender;
	}
}