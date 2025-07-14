package self.project.task_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/secure/test")
    public String testSecurity() {
        return "Security check passed!";
    }
}