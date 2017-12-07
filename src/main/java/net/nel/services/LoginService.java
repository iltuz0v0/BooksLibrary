package net.nel.services;

import net.nel.dao.LoginDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginDAOImpl loginDAO;

    private boolean isUsernameExists(String login) {
        return loginDAO.isUsernameExists(login);
    }

    public boolean isUserRegister(String login, String password) {
        boolean isUserRegister = false;
        if (!loginDAO.isUsernameExists(login)) {
            if (loginDAO.userRegister(login, password)) {
                isUserRegister = true;
            }
        } else isUserRegister = false;
        return isUserRegister;
    }
}
