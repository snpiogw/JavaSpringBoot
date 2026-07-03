package sample.common.service;

@Service

public class DbUserDetailsService implements UserDetailsService {
    private final LoginMapper loginMapper;
    public DbUserDetailsService(LoginMapper loginMapper) { this.loginMapper = loginMapper; }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Login user = loginMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }
        return User.withUsername(user.getUsername())
                   .password(user.getPassword()) // BCryptハッシュ
                   .roles("USER")
                   .build();
    }
}
