package edu.greenriver.sdev.myspringproject.controllers;

import edu.greenriver.sdev.myspringproject.models.Authority;
import edu.greenriver.sdev.myspringproject.models.Package;
import edu.greenriver.sdev.myspringproject.models.User;
import edu.greenriver.sdev.myspringproject.services.LoginService;
import edu.greenriver.sdev.myspringproject.services.PackageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Bessarab
 * @version 1.0
 *
 * This is the controller class for the index page
 */
@Controller
public class IndexController {
    private PackageService service;
    private LoginService userService;

    public IndexController(PackageService service, LoginService userService) {
        this.service = service;
        this.userService = userService;
    }

    //main page
    @RequestMapping({"", "/", "/index", "index.html"})
    public String index() {
        return "index";
    }

    //summary page
    @RequestMapping("detailing/summary")
    public String summary(Model model) {
        model.addAttribute("packages", service.allPackages());
        return "summary";
    }

    //individual element page
    @RequestMapping("detailing/package/{id}")
    public String user(Model model, @PathVariable int id) {
        model.addAttribute("package_single", service.packageById(id));
        return "package";
    }

    //Consumer Page
    @RequestMapping("detailing/consumer")
    public String consumer() {
        return "consumer";
    }

    //register page
    @GetMapping("register")
    public String loadRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    //access denied page
    @RequestMapping("/denied")
    public String denied() {
        return "denied";
    }

    @PostMapping("register")
    public String submitRegistration(@ModelAttribute User user) {
        Authority userRole = new Authority();
        userRole.setRole("ROLE_USER");
        userRole.setUser(user);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(userRole);
        user.setAuthorities(authorities);

        userService.saveUser(user);
        return "redirect:/index";
    }

    //Admin Page
    @RequestMapping("detailing/admin")
    public String admin() {
        return "admin";
    }

    //CREATE
    @GetMapping("detailing/add/form")
    public String loadForm(Model model) {
        model.addAttribute("single", new Package());
        return "add_package";
    }

    @PostMapping("detailing/add/form")
    public String submit(@ModelAttribute Package single) {
        if(single.getPackage_name().equals("Bronze")) {
            single.setPrice(100);
            single.setPoints(200);
        }
        else if(single.getPackage_name().equals("Silver")) {
            single.setPrice(90);
            single.setPoints(100);
        }
        else if(single.getPackage_name().equals("Gold")) {
            single.setPrice(170);
            single.setPoints(300);
        }
        else {
            single.setPrice(230);
            single.setPoints(400);
        }

        //if additional option is chosen than + $30 to the price
        if(single.isAdditional_options()) {
            single.setPrice(single.getPrice() + 30);
        }

        service.savePackage(single);
        return "redirect:/detailing/summary";
    }

    //UPDATE
    @GetMapping("detailing/update/{id}")
    public String loadEditForm(Model model, @PathVariable int id) {
        model.addAttribute("single", service.packageById(id));
        return "add_package";
    }

    //DELETE
    @GetMapping("detailing/delete/{id}")
    public String deleteEntry(@PathVariable int id) {
        service.deletePackage(service.packageById(id));
        return "redirect:/detailing/summary";
    }
}