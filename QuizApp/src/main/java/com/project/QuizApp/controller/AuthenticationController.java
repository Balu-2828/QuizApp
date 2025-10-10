package com.project.QuizApp.controller;

import com.project.QuizApp.JwtUtil.JwtUtil;
import com.project.QuizApp.dao.UserRepository;
import com.project.QuizApp.model.UserAndAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private  final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody UserAndAdmin user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserAndAdmin user) {
        UserAndAdmin existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {

            return jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().name());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


    @GetMapping("/test")
    public String test(){
        return "Authentication is working";
    }
}

