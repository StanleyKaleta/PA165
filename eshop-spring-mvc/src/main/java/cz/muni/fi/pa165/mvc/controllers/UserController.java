package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.facade.UserFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    UserFacade userFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("users", userFacade.getAllUsers());

        return "user/list";
    }
}
