package spring.restful.login.v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.restful.login.v1.entity.ERole;
import spring.restful.login.v1.entity.Role;
import spring.restful.login.v1.entity.User;
import spring.restful.login.v1.exception.CustomAPIException;
import spring.restful.login.v1.payload.LoginDto;
import spring.restful.login.v1.payload.RegisterDto;
import spring.restful.login.v1.repository.RoleRepository;
import spring.restful.login.v1.repository.UserRepository;
import spring.restful.login.v1.security.JwtTokenProvider;
import spring.restful.login.v1.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public String login(LoginDto loginDto) {
        userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email :"+loginDto.getUsernameOrEmail()));


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        //check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new CustomAPIException(HttpStatus.BAD_REQUEST, "Email is already exists");
        }

        //check for username exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new CustomAPIException(HttpStatus.BAD_REQUEST, "Username is already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
