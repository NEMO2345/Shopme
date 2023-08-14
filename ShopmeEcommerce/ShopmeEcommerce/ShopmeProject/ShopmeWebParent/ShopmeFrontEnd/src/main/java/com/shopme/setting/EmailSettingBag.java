package com.shopme.setting;

import java.util.List;

import com.shopme.common.entity.setting.Setting;
import com.shopme.common.entity.setting.SettingBag;

public class EmailSettingBag extends SettingBag {

	public EmailSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}

	public String getHost() {
		return super.getValue("MAIL_HOST");
	}
	
	
	 public int getPort() { 
		 return Integer.parseInt(super.getValue("MAIL_PORT"));
	 }
	 
	/*
	 * public int getPort() { String portValue = super.getValue("MAIL_PORT"); if
	 * (portValue != null && !portValue.isEmpty()) { return
	 * Integer.parseInt(portValue); } else { // Xử lý khi chuỗi rỗng hoặc null // Ví
	 * dụ: Trả về giá trị mặc định hoặc ném một ngoại lệ phù hợp return 0; // Giá
	 * trị mặc định là 0 } }
	 */
	
	public String getUsername() {
		return super.getValue("MAIL_USERNAME");
	}
	
	public String getPassword() {
		return super.getValue("MAIL_PASSWORD");
	}
	
	public String getSmtpAuth() {
		return super.getValue("SMTP_AUTH");
	}
	
	public String getSmtpSecured() {
		return super.getValue("SMTP_SECURED");
	}
	
	public String getFromAddress() {
		return super.getValue("MAIL_FROM");
	}
	
	public String getSenderName() {
		return super.getValue("MAIL_SENDER_NAME");
	}
	
	public String getCustomerVerifySubject() {
		return super.getValue("CUSTOMER_VERIFY_SUBJECT");
	}
	
	public String getCustomerVerifyContent() {
		return super.getValue("CUSTOMER_VERIFY_CONTENT");
	}
}
