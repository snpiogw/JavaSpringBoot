package sample.common.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sample.common.dao.entity.Login;

@Mapper
public interface LoginMapper {
    void insertUser(Login login);
    Login findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}