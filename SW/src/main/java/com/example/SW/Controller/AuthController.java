package com.example.SW.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SW.Repository.DriverRepository;
import com.example.SW.Repository.UserRepository;
import com.example.SW.config.JwtUti;
import com.example.SW.domain.UserRole;
import com.example.SW.exceptions.UserException;
import com.example.SW.model.Driver;
import com.example.SW.model.User;
import com.example.SW.request.DriverSignupRequest;
import com.example.SW.request.LoginRequest;
import com.example.SW.request.SignupRequest;
import com.example.SW.response.JwtResponse;
import com.example.SW.service.CustomeUserDetailsService;
import com.example.SW.service.DriverService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepository;
    private DriverRepository driverRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUti jwtUtil;
    private CustomeUserDetailsService customeUserDetailsService;
    private DriverService driverService;

    public AuthController(UserRepository userRepository, DriverRepository driverRepository,
            PasswordEncoder passwordEncoder, JwtUti jwtUtil, DriverService driverService,
            CustomeUserDetailsService customeUserDetailsService) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.driverService = driverService;
        this.customeUserDetailsService = customeUserDetailsService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignupRequest signupRequest)
            throws UserException, AuthenticationException {

        User user = userRepository.findByEmail(signupRequest.getEmail());

        JwtResponse jwtResponse = new JwtResponse();

        if (user != null) {
            throw new UserException("User Already Exist With Email " + signupRequest.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // Create new user account
        User createdUser = new User();
        createdUser.setEmail(signupRequest.getEmail());
        createdUser.setPassword(encodedPassword);
        createdUser.setFullname(signupRequest.getFullName());
        createdUser.setMobile(signupRequest.getMobile());
        createdUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtUtil.generateJwtTOken(authentication);

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setType(UserRole.USER);
        jwtResponse.setMessage("Account Created Successfully: " + savedUser.getFullname());

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.ACCEPTED);

    }

    @PostMapping("/driver/signup")
    public ResponseEntity<JwtResponse> driverSignupHandler(@RequestBody DriverSignupRequest driverSignupRequest) {

        Driver driver = driverRepository.findByEmail(driverSignupRequest.getEmail());

        JwtResponse jwtResponse = new JwtResponse();

        if (driver != null) {

            jwtResponse.setAuthenticated(false);
            jwtResponse.setErrorDetails("email already used with another account");
            jwtResponse.setError(true);

            return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.BAD_REQUEST);
        }

        Driver createdDriver = driverService.registerDriver(driverSignupRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(),
                createdDriver.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtTOken(authentication);

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setType(UserRole.DRIVER);
        jwtResponse.setMessage("Account Created Successfully: " + createdDriver.getName());
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtTOken(authentication);

        JwtResponse jwtResponse = new JwtResponse();

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setMessage("Account Logging in  Successfully: ");
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        System.out.println("sign in userDetails - " + password);
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);

        System.out.println("sign in userDetails after loaduser- " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
