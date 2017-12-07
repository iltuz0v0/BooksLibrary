package net.nel.dao;

import net.nel.models.Book;
import net.nel.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@PropertySource("/WEB-INF/conf/config.properties")
public class BookDAOImpl implements BookDAO {
    @Autowired
    private Environment env;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooks() {
        return jdbcTemplate.query(env.getProperty("GET_BOOKS"), new BeanPropertyRowMapper<Book>(Book.class));
    }

    public int setBook(Book book) {
        Object[] objects = {book.getFilename(), book.getImagename(), book.getLength(), book.getBookname(), book.getAuthor(), book.getDescription(), book.getId_user()};
        return jdbcTemplate.update(env.getProperty("SET_BOOK"), objects);
    }

    public Login getAuthor(int id) {
        Object[] objects = {id};
        return jdbcTemplate.queryForObject(env.getProperty("GET_AUTHOR"), objects, new BeanPropertyRowMapper<Login>(Login.class));
    }

    public Book getBookById(int id) {
        Object[] objects = {id};
        return jdbcTemplate.queryForObject(env.getProperty("GET_BOOK_BY_ID"), objects, new BeanPropertyRowMapper<Book>(Book.class));
    }

    public int updateBookById(Book book, int id) {
        Object[] objects = {book.getBookname(), book.getAuthor(), book.getDescription(), id};
        return jdbcTemplate.update(env.getProperty("UPDATE_BOOK_BY_ID"), objects);
    }

    public int deleteBookById(int id) {
        Object[] objects = {id};
        return jdbcTemplate.update(env.getProperty("DELETE_BOOK_BY_ID"), objects);
    }

    public Login getUser(String login) {
        Object[] objects = {login};
        Login userLogin = null;
        ArrayList<Login> logins = (ArrayList<Login>) jdbcTemplate.query(env.getProperty("GET_USER"), objects, new BeanPropertyRowMapper<Login>(Login.class));
        if (logins.size() != 0) userLogin = logins.get(0);
        return userLogin;
    }
}
