package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.CategoryCreateDTO;
import cz.fi.muni.pa165.dto.ProductCreateDTO;
import cz.fi.muni.pa165.facade.CategoryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final static Logger log = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryFacade categoryFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("categories", categoryFacade.getAllCategories());
        return "category/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newCategory(Model model) {
        model.addAttribute("categoryCreate", new CategoryCreateDTO());
        return "category/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("categoryCreate") CategoryCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(categoryCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "category/new";
        }
        //create product
        Long id = categoryFacade.createCategory(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Category " + id + " was created");
        return "redirect:" + uriBuilder.path("/category/list").encode().toUriString();
    }

}
