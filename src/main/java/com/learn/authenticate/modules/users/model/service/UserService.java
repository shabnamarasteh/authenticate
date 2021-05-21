package com.learn.authenticate.modules.users.model.service;

import com.learn.authenticate.modules.users.model.component.EmailDataDTO;
import com.learn.authenticate.modules.users.model.entity.User;
import com.learn.authenticate.modules.users.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class UserService {
    private UserRepository userRepository;
    private SpringTemplateEngine templateEngine;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, SpringTemplateEngine templateEngine, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User save(User user) {
        System.out.println("-------------36--------"+user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        EmailDataDTO emailDataDTO = new EmailDataDTO();
        emailDataDTO.setEmail(user.getEmail());
        String fullName = (user.getFirstName() != null ? user.getFirstName() : "") + " " + (user.getLastName() != null ? user.getLastName() : "");
        emailDataDTO.setFullName(fullName.trim());
        emailDataDTO.setSubject("Welcome :)");
        try {
            this.sendMail(emailDataDTO);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void sendMail(EmailDataDTO emailDataDTO) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shabnaam.ar94@gmail.com", "sh@bn@mgm@il");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("shabnaam.ar94@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDataDTO.getEmail()));
        msg.setSubject(emailDataDTO.getSubject());
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        Context thymeleafContext = new Context();
        Map<String, Object> msg_param = new ConcurrentHashMap<>();
        Map<String, String> msg_Object = new ConcurrentHashMap<>();
        msg_Object.put("email" , emailDataDTO.getEmail());
        msg_Object.put("fullName" , emailDataDTO.getFullName());
        msg_Object.put("subject" , emailDataDTO.getSubject());
        msg_Object.put("senderName" , "shabnam.ar94");
        msg_param.put("variables", msg_Object);

        thymeleafContext.setVariables(msg_param);
        messageBodyPart.setContent(templateEngine.process("welcomeMail.html", thymeleafContext), "text/html");
        msg.setContent(templateEngine.process("welcomeMail.html", thymeleafContext), "text/html");

        Transport.send(msg);
    }

    public User findById(Long userId) {
        return this.userRepository.getOne(userId);
    }

    public User loginUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        User userExist = this.userRepository.findByEmail(user.getEmail());
        if(passwordEncoder.matches(userExist.getPassword(), password)){
            return userExist;
        }else {
            return null;
        }
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}