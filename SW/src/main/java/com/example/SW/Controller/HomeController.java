package com.example.SW.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.SW.Repository.DriverRepository;
import com.example.SW.Repository.UserRepository;
import com.example.SW.config.JwtUti;
import com.example.SW.exceptions.UserException;
import com.example.SW.model.Driver;
import com.example.SW.model.User;
import com.example.SW.response.MessageResponse;

@Controller
public class HomeController {
    @Autowired
    private JwtUti jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @GetMapping({ "/api", "/" })
    public ResponseEntity<MessageResponse> homeController() {
        MessageResponse res = new MessageResponse("welcome to cab booking api");

        return new ResponseEntity<MessageResponse>(res, HttpStatus.ACCEPTED);

    }

    @GetMapping("/api/profile")
    public ResponseEntity<?> userProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        String email = jwtUtil.getEmailFromJwt(jwt);

        if (email == null) {
            throw new BadCredentialsException("invalid token recived");
        }

        Driver driver = driverRepository.findByEmail(email);

        if (driver != null) {
            return new ResponseEntity<Driver>(driver, HttpStatus.ACCEPTED);
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            System.out.println("user - " + user.getEmail());
            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        }

        throw new UserException("user not found with email " + email);
    }
}
