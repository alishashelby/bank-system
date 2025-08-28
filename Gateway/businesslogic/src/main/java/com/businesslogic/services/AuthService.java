package com.businesslogic.services;

import com.businesslogic.dto.LoginRequest;
import com.businesslogic.dto.LoginResponse;
import com.businesslogic.dto.RegisterAdminRequest;
import com.businesslogic.dto.RegisterUserRequest;
import com.businesslogic.exceptions.UserExistsException;
import com.businesslogic.exceptions.UserNotFoundException;
import com.businesslogic.security.jwt.JwtUtils;
import com.dataaccess.entities.User;
import com.dataaccess.enums.Role;
import com.dataaccess.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dataaccess.entities.User;


import java.util.Optional;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerAdmin(RegisterAdminRequest registerAdminRequest) throws UserExistsException {
        Optional<User> optionalUser = userRepository.findByLogin(registerAdminRequest.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserExistsException(registerAdminRequest.getLogin());
        }

        User user = new User(registerAdminRequest.getLogin(),
                passwordEncoder.encode(registerAdminRequest.getPassword()),
                Role.ADMIN);

        User savedUser = userRepository.save(user);
    }

    @Transactional
    public void registerUser(RegisterUserRequest registerUserRequest) throws UserExistsException {
        Optional<User> optionalUser = userRepository.findByLogin(registerUserRequest.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserExistsException(registerUserRequest.getLogin());
        }

        User user = new User(registerUserRequest.getLogin(),
                passwordEncoder.encode(registerUserRequest.getPassword()),
                Role.CLIENT);

        User savedUser = userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) throws UserNotFoundException {
        userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getLogin()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwt);

        return loginResponse;
    }
}
