package net.nel.dao;

public interface LoginDAO {
    public boolean isUsernameExists(String login);

    public boolean userRegister(String login, String password);
}
