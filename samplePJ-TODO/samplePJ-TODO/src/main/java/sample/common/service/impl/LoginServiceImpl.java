package sample.common.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(LoginMapper loginMapper, PasswordEncoder passwordEncoder) {
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(String username, String rawPassword) {
        if (loginMapper.findByUsername(username) != null) {
            throw new IllegalStateException("このユーザー名は既に使われています");
        }

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(passwordEncoder.encode(rawPassword));

        loginMapper.insertUser(login);
    }
}