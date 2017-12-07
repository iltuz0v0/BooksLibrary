package net.nel.controllers;

import net.nel.models.Login;
import net.nel.services.LoginService;
import net.nel.subclasses.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@PropertySource("/WEB-INF/conf/config.properties")
@Controller
public class LoginController {
    @Autowired
    private SignUpValidator signUpValidator;
    @Autowired
    private LoginService loginService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName(env.getProperty("LOGIN_PAGE"));
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView intro(ModelAndView model) {
        model.setViewName("redirect:/" + env.getProperty("MAIN_PAGE") + "?number=1");
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegistration(ModelAndView model) {
        model.setViewName(env.getProperty("REGISTRATION_PAGE"));
        model.addObject(env.getProperty("LOGIN_OBJECT"), new Login());
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView postRegistration(@ModelAttribute Login login, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.setViewName(env.getProperty("REGISTRATION_PAGE"));
        signUpValidator.validate(login, result);
        if (!result.hasErrors()) {
            if (isSuccessfulRegistration(login.getLogin(), login.getPassword())) {
                model.addObject(env.getProperty("REGISTRATION_VARIABLE_NAME"),
                        env.getProperty("RIGHT_REGISTRATION_MESSAGE"));
            } else {
                model.addObject(env.getProperty("REGISTRATION_VARIABLE_NAME"),
                        env.getProperty("WRONG_REGISTRATION_MESSAGE"));
            }
        }
        return model;
    }

    private boolean isSuccessfulRegistration(String login, String password) {
        return loginService.isUserRegister(login, password);
    }
}
