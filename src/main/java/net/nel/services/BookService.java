package net.nel.services;

import net.nel.dao.BookDAOImpl;
import net.nel.models.Book;
import net.nel.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDAOImpl bookDAO;

    public List<Book> getBooks(int page, int count) {
        ArrayList<Book> books = (ArrayList<Book>) bookDAO.getBooks();
        if (page * count <= books.size()) {
            return books.subList(page * count - count, page * count);
        } else {
            return books.subList(page * count - count, books.size());
        }
    }

    public boolean setBook(Book book) {
        return bookDAO.setBook(book) > 0;
    }

    public Book getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    public boolean updateBookById(Book book, int id) {
        return bookDAO.updateBookById(book, id) > 0;
    }

    public boolean deleteBookById(int id) {
        return bookDAO.deleteBookById(id) > 0;
    }

    public String getAuthorLogin(int id) {
        Login user = bookDAO.getAuthor(id);
        return user.getLogin();
    }

    public Login getUser(String login) {
        return bookDAO.getUser(login);
    }

    public LinkedList<Integer> booleanBlock(int page, int count, int block) {
        ArrayList<Book> books = (ArrayList<Book>) bookDAO.getBooks();
        LinkedList<Integer> pages = new LinkedList<Integer>();
        if (page > 1) pages.add(page - 1);
        pages.add(page);
        for (int i = 0; i < block; i++) {
            if (books.size() > page * count * (i + 1)) {
                pages.add(page + i + 1);
            }
        }
        if (pages.size() < block) {
            for (int i = block - pages.size(); i > 0; i--) {
                pages.addFirst(i);
            }
        }
        return pages;
    }
}
