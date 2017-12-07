package net.nel.dao;

import net.nel.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@PropertySource("/WEB-INF/conf/config.properties")

public class LoginDAOImpl implements LoginDAO {
    @Autowired
    private Environment env;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isUsernameExists(String login) {
        Object[] objects = {login};
        ArrayList<Login> logins = (ArrayList<Login>) jdbcTemplate.query(env.getProperty("IS_USERNAME_EXISTS"), objects, new BeanPropertyRowMapper<Login>(Login.class));
        return logins.size() != 0;
    }

    public boolean userRegister(String login, String password) {
        return jdbcTemplate.update(env.getProperty("USER_REGISTER"), login, password, login) > 0;
    }
}
