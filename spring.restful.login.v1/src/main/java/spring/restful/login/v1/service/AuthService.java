package spring.restful.login.v1.service;

import spring.restful.login.v1.payload.LoginDto;
import spring.restful.login.v1.payload.RegisterDto;

public interface AuthService {
    public String login(LoginDto loginDto);

    public String register(RegisterDto registerDto);
}
