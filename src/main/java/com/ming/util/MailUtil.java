package com.ming.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Ming 邮箱发送工具<br />
 * 参照<a href="http://blog.163.com/java_star/blog/static/11771480520136511023957/">http://blog.163.com/java_star/blog/static/11771480520136511023957/</a>
 */
public class MailUtil {
	private static boolean isInited = false;
	private static Properties properties = new Properties();

	public static void sendInBackground(String address, String subject, String content) {
		new SendTask(address, subject, content).start();
	}
	
	public static boolean send(String address, String subject, String content) {
		return new SendTask(address, subject, content).send();
	}
	
	public static void sendToAdminInBackground(String subject, String content) {
		new SendTask(null, subject, content).start();
	}
	
	public static boolean sendToAdmin(String subject, String content) {
		return new SendTask(null, subject, content).send();
	}
	
	public static void init() {
		try {
			// 配置发送邮件的环境属性
			InputStream input = MailUtil.class.getResource("/mail.properties").openStream();
			properties.load(input);
			isInited = true;
			System.out.println(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class SendTask extends Thread {
		private String address;
		private String subject;
		private String content;
		
		@Override
		public void run() {
			send();
		}
		
		public boolean send() {
			try {
				if (!isInited)
					init();
				
				if (properties.getProperty("status").equals("off"))
					return false;
				
				// 构建授权信息，用于进行SMTP进行身份验证
				Authenticator authenticator = new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// 用户名、密码
						String userName = properties.getProperty("mail.user");
						String password = properties.getProperty("mail.password");
						return new PasswordAuthentication(userName, password);
					}
				};
				
				// 使用环境属性和授权信息，创建邮件会话
				Session mailSession = Session.getInstance(properties, authenticator);
				// 创建邮件消息
				MimeMessage message = new MimeMessage(mailSession);
				// 设置发件人
				InternetAddress form = new InternetAddress(properties.getProperty("mail.user"));
				message.setFrom(form);
				
				// 设置收件人
				InternetAddress to = new InternetAddress(address == null ? properties.getProperty("admin") : address);
				message.setRecipient(RecipientType.TO, to);
				
				// 设置邮件标题
				message.setSubject(subject);
				
				// 设置邮件的内容体
				message.setContent(content, "text/html;charset=UTF-8");
				
				// 发送邮件
				Transport.send(message);
				
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public SendTask(String address, String subject, String content) {
			this.address = address;
			this.subject = subject;
			this.content = content;
		}
	}
	
	public static void main(String[] args) {
		init();
		System.out.println(properties);
	}
}
