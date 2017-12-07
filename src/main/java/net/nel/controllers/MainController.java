package net.nel.controllers;

import net.nel.models.Book;
import net.nel.models.Login;
import net.nel.services.BookService;
import net.nel.subclasses.AdditionValidator;
import net.nel.subclasses.UpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@PropertySource("/WEB-INF/conf/config.properties")
@RequestMapping(value = "/main")
@SessionAttributes("session_login")
public class MainController {
    private static int postsOnThePage = 5;
    private static int extraReferances = 3;
    @Autowired
    private Environment env;
    @Autowired
    private AdditionValidator additionValidator;
    @Autowired
    private UpdateValidator updateValidator;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "")
    public ModelAndView main(@RequestParam("number") Integer pageNumber, ModelAndView model) {
        model.addObject("session_login", setUserSessionInformation());
        model.addObject(env.getProperty("NICKNAME_VARIABLE"),
                setUserSessionInformation().getNickname());
        model.addObject(env.getProperty("PAGE_OBJECTS"),
                bookService.booleanBlock(pageNumber, postsOnThePage, extraReferances));
        model.addObject(env.getProperty("BOOK_OBJECTS"),
                bookService.getBooks(pageNumber, postsOnThePage));
        model.addObject(env.getProperty("LOGIN_ID_VARIABLE"), setUserSessionInformation().getId());
        model.setViewName(env.getProperty("MAIN_PAGE"));
        return model;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddBook(@ModelAttribute("session_login") Login login, ModelAndView model) {
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), login.getNickname());
        model.setViewName(env.getProperty("BOOK_ADD_PAGE"));
        model.addObject(env.getProperty("BOOK_OBJECT"), new Book());
        return model;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView postAddBook(@ModelAttribute("session_login") Login user,
                                    @ModelAttribute Book book, BindingResult result) throws IOException {
        ModelAndView model = new ModelAndView(env.getProperty("BOOK_ADD_PAGE"));
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        additionValidator.validate(book, result);
        if (!result.hasErrors()) {
            model.addObject(env.getProperty("ADDITION_VARIABLE_NAME"),
                    env.getProperty("RIGHT_ADDITION_FILE"));
            if (!isWrittenToFile(user, book)) {
                model.addObject(model.addObject(env.getProperty("ADDITION_VARIABLE_NAME"),
                        env.getProperty("WRONG_ADDITION_FILE")));
            } else {
                if (!bookService.setBook(new Book(book.getFile().getOriginalFilename(),
                        book.getImage().getOriginalFilename(), book.getImage().getSize(),
                        book.getBookname(), book.getAuthor(), book.getDescription(), user.getId()))) {
                    model.addObject(model.addObject(env.getProperty("ADDITION_VARIABLE_NAME"),
                            env.getProperty("WRONG_ADDITION_FILE")));
                }
            }
        }
        return model;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView getUpdateBook(@ModelAttribute("session_login") Login user,
                                      @PathVariable int id, ModelAndView model) {
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        if (!isPostAuthor(id, user))
            model.setViewName("redirect:/" + env.getProperty("MAIN_PAGE"));
        else
            model.setViewName(env.getProperty("BOOK_UPDATE_PAGE"));
        model.addObject(env.getProperty("BOOK_OBJECT"), bookService.getBookById(id));
        return model;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView postUpdateBook(@ModelAttribute("session_login") Login user,
                                       @PathVariable int id,
                                       @ModelAttribute Book book, BindingResult result) {
        updateValidator.validate(book, result);
        ModelAndView model = new ModelAndView(env.getProperty("BOOK_UPDATE_PAGE"));
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        if (!result.hasErrors()) {
            if (bookService.updateBookById(book, id)) {
                model.addObject(env.getProperty("UPDATE_VARIABLE_NAME"),
                        env.getProperty("RIGHT_UPDATE_FILE"));
            } else {
                model.addObject(env.getProperty("UPDATE_VARIABLE_NAME"),
                        env.getProperty("WRONG_UPDATE_FILE"));
            }
        }
        return model;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView getDeleteBook(@ModelAttribute("session_login") Login user,
                                      @PathVariable int id, ModelAndView model) {
        if (!isPostAuthor(id, user))
            model.setViewName("redirect:/" + env.getProperty("MAIN_PAGE"));
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        model.setViewName(env.getProperty("BOOK_DELETE_PAGE"));
        if (bookService.deleteBookById(id)) {
            model.addObject(env.getProperty("DELETE_VARIABLE_NAME"),
                    env.getProperty("RIGHT_DELETE_FILE"));
        } else {
            model.addObject(env.getProperty("DELETE_VARIABLE_NAME"),
                    env.getProperty("WRONG_DELETE_FILE"));
        }
        return model;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public ModelAndView getDownloadBook(@ModelAttribute("session_login") Login user,
                                        @PathVariable int id, ModelAndView model) {
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        model.setViewName(env.getProperty("BOOK_DOWNLOAD_PAGE"));
        model.addObject(env.getProperty("BOOK_OBJECT"), bookService.getBookById(id));
        return model;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.POST)
    public @ResponseBody
    void postDownloadBook(@ModelAttribute("session_login") Login user,
                          @PathVariable int id, HttpServletResponse response) throws IOException {
        Book book = bookService.getBookById(id);
        ModelAndView model = new ModelAndView(env.getProperty("BOOK_DOWNLOAD_PAGE"));
        model.addObject(env.getProperty("NICKNAME_VARIABLE"), user.getNickname());
        if (!isReadFromFile(response, book)) {
            model.addObject("DOWNLOAD_VARIABLE_NAME", "WRONG_DOWNLOAD_FILE");
        }
    }

    @RequestMapping(value = "/getimage/{length}/{id}", method = RequestMethod.GET)
    public @ResponseBody
    void getDownloadBook(@PathVariable int length, @PathVariable int id,
                         HttpServletResponse response) throws IOException {
        File file = new File(env.getProperty("FILES_CONTAINER_PATH") + bookService.getAuthorLogin(bookService.getBookById(id).getId_user()) + "/" + bookService.getBookById(id).getImagename());
        InputStream fileInputStream = new FileInputStream(file);
        response.setContentType("image/*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + bookService.getBookById(id).getImagename());
        response.setHeader("Content-Length", String.valueOf(length));
        FileCopyUtils.copy(fileInputStream, response.getOutputStream());
    }

    private boolean writeFile(Login login, Book book) throws IOException {
        boolean result = true;
        File folder = new File(env.getProperty("FILES_CONTAINER_PATH") + login.getLogin());
        if (!folder.exists()) {
            result = folder.mkdir();
        }
        if (result && isSuitableContentType(book)) {
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(env.getProperty("FILES_CONTAINER_PATH") + login.getLogin() + "/" + new File(book.getFile().getOriginalFilename())));
            fileOutputStream.write(book.getFile().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(env.getProperty("FILES_CONTAINER_PATH") + login.getLogin() + "/" + new File(book.getImage().getOriginalFilename())));
            fileOutputStream.write(book.getImage().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } else {
            result = false;
        }
        return result;
    }

    private boolean readFile(HttpServletResponse response, Book book) throws IOException {
        File file = new File(env.getProperty("FILES_CONTAINER_PATH") + bookService.getAuthorLogin(book.getId_user()) + "/" + book.getFilename());
        InputStream fileInputStream = new FileInputStream(file);
        response.setContentType("application/*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        return true;
    }

    private Login setUserSessionInformation() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookService.getUser(userDetails.getUsername());
    }

    private boolean isWrittenToFile(Login user, Book book) throws IOException {
        return writeFile(user, book);
    }

    private boolean isReadFromFile(HttpServletResponse response, Book book) throws IOException {
        return readFile(response, book);
    }

    private boolean isPostAuthor(int id, Login user) {
        return user.getId() == bookService.getBookById(id).getId_user();
    }

    private boolean isSuitableContentType(Book book) {
        boolean suiltableImageContentType;
        boolean suiltableFileContentType;
        String imageContentType = book.getImage().getContentType();
        String fileContentType = book.getFile().getContentType();
        suiltableImageContentType = imageContentType.equals("image/png") ||
                imageContentType.equals("image/jpeg") || imageContentType.equals("image/jpg");
        suiltableFileContentType = fileContentType.equals("application/pdf") ||
                fileContentType.equals("application/fb2") || fileContentType.equals("application/epub");
        return suiltableImageContentType && suiltableFileContentType;
    }
}