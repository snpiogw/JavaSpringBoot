package sample.common.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginMapper loginMapper;

    public LoginUserDetailsService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // データベースからユーザーを取得
        Login user = loginMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }

        // Spring Security用のユーザーオブジェクトに変換して返す
        return User.withUsername(user.getUsername())
                   .password(user.getPassword()) // ハッシュ化されたパスワード
                   .roles("USER") // 権限（今回はとりあえずUSERでOK）
                   .build();
    }
}