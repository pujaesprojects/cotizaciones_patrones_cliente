package edu.puj.patrones.cliente.controller;

import edu.puj.patrones.cliente.util.EmailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailUtil emailUtil;

    public EmailController(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @GetMapping("/send")
    public void sendEmail(@RequestBody String to) {
        emailUtil.sendEmail(to);
    }
}
