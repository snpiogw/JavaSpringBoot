package sample.common.service;
import sample.common.dao.entity.Login;

public interface LoginService {
    void registerUser(String username, String password);
    Login login(String username, String password);
}