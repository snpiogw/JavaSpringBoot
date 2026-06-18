package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public void registerUser(String username, String password) {
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        loginMapper.insertUser(login);
    }

    @Override
    public Login login(String username, String password) {
        return loginMapper.findByUsernameAndPassword(username, password);
    }
}