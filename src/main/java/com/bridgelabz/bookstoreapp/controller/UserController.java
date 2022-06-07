package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;

import com.bridgelabz.bookstoreapp.service.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/bookstoreApi")
public class UserController {

    @Autowired
    private EmailSenderService senderService;

    /**
     * Checking Api working or not
     * @return
     */
    @GetMapping({"/hello", "/"})
    public String welcomeApi() {
        return "Hello Welcome To BookStore App";
    }


    /**
     * @Purpose - To check if the test mail is sending or not
     */
    @GetMapping("/email/{toEmail}/{subject}/{body}")
    public ResponseEntity<ResponseDTO> sendTestEmail(@PathVariable String toEmail,
                                                     @PathVariable String subject,
                                                     @PathVariable String body) {
        senderService.sendEmail(toEmail,
                subject,
                body);
        ResponseDTO responseDTO = new ResponseDTO("Sent Email Successfully", toEmail);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    /**
     * Attach any pdf or any attachment
     * @return
     * @throws MessagingException
     */
    @GetMapping("/emailAttached")
    public ResponseEntity<ResponseDTO> sendEmailWithAttachment() throws MessagingException {
        senderService.sendEmailWithAttachment("shardulkumbhar99@gmail.com",
                "This is subject",
                "Hai Sharky find the attachment below",
                "C:\\Users\\shard\\Downloads\\whatapp image\\IMG-20210710-WA0109.jpg");
        ResponseDTO responseDTO = new ResponseDTO("Sent Email Successfully", "toEmail");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}

