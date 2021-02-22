package com.capstone.project.security;


import com.capstone.project.ProjectApplication;
import com.capstone.project.exception.BadRequestException;
import com.capstone.project.security.models.AuthenticationRequest;
import com.capstone.project.security.models.AuthenticationResponse;
import com.capstone.project.security.user.MyUserDetailsService;
import com.capstone.project.security.user.UserRepository;
import com.capstone.project.security.user.model.User;
import com.capstone.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthController {
  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtUtil jwtTokenUtil;

  @Autowired private MyUserDetailsService userDetailsService;

  @Autowired private UserRepository userRepository;

  @GetMapping("/auth")
  public String auth(Principal principal){
    return principal.getName();
  }

  @PostMapping( "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public String signup(@RequestBody AuthenticationRequest authenticationRequest)
       {
    final Optional<User> user = userRepository.findByUserName(authenticationRequest.getUsername());
    if (user.isPresent()) {
     throw new BadRequestException(String.format("Username %s Is Already exist", authenticationRequest.getUsername()));
    }
    User user1 = new User();
    user1.setUserName(authenticationRequest.getUsername());
    user1.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
    user1.setActive(true);
    user1.setRoles("user");
    userRepository.save(user1);
    return "Signup Successfully";
  }

  @GetMapping("/")
  public Principal getHello(Principal principal){
    System.out.println(principal);
    return principal;
  }
}
