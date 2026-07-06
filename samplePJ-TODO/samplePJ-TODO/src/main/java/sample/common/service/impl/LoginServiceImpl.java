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
        login.setPassword(passwordEncoder.encode(rawPassword)); // ハッシュ化
        loginMapper.insertUser(login);
    }

    @Override
    @Transactional(readOnly = true)
    public Login login(String username, String rawPassword) {
        // 1. ユーザー名で検索
        Login user = loginMapper.findByUsername(username);
        
        // --- ここにデバッグログを追加 ---
        System.out.println("DEBUG: 検索結果(user)はnullか？ -> " + (user == null));
        if (user != null) {
            System.out.println("DEBUG: データベース上のパスワード(ハッシュ値) -> " + user.getPassword());
            boolean isMatch = passwordEncoder.matches(rawPassword, user.getPassword());
            System.out.println("DEBUG: パスワード照合結果 -> " + isMatch);
        }
        // ------------------------------

        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }
}