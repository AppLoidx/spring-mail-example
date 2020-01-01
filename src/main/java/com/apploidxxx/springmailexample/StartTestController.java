package com.apploidxxx.springmailexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/start")
public class StartTestController {

    private final EmailServiceImpl emailService;

    public StartTestController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/simple")
    public void simpleMessage() {
        emailService.sendSimpleMessage("to_email@gmail.com", "Arthur", "Hello!");
    }

    @GetMapping("attachment")
    public void withAttachment() throws MessagingException {
        emailService.sendEmailWithAttachment("to_email@gmail.com", "Arthur");
    }
}
