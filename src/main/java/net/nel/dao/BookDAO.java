package net.nel.dao;

import net.nel.models.Book;

import java.util.List;

public interface BookDAO {
    public List<Book> getBooks();

    public int setBook(Book book);
}
