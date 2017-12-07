package net.nel.models;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class Book {
    private int id;
    private String filename;
    private String imagename;
    private long length;
    private String bookname;
    private String author;
    private String description;
    private int id_user;
    private CommonsMultipartFile file;
    private CommonsMultipartFile image;

    public Book() {
    }

    public Book(String filename, String imagename, long length, String bookname,
                String author, String description, int id_user) {
        this.length = length;
        this.filename = filename;
        this.imagename = imagename;
        this.bookname = bookname;
        this.author = author;
        this.description = description;
        this.id_user = id_user;
    }

    public Book(String filename, String bookname, String author, String description) {
        this.filename = filename;
        this.bookname = bookname;
        this.author = author;
        this.description = description;
    }

    public long getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return bookname;
    }

    public void setName(String name) {
        this.bookname = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
