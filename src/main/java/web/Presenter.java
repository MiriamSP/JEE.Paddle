package web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.CourtController;
import business.wrapper.CourtState;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class Presenter {

    @Autowired
    private CourtController courtController;

    private static final List<String> THEMES = Arrays.asList("jsp", "bootstrap", "thymeleaf");

    private static final String themeChoose = "jsp";

    private String theme = THEMES.get(0);

    public Presenter() {
    }

    // Se ejecuta siempre y antes. Añade un atributo al Model
    @ModelAttribute("now")
    public String now() {
        return new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss").format(new Date());
    }

    @RequestMapping("/home")
    public String home(Model model) {

        model.addAttribute("themes", THEMES);
        // La vista resultante no lleva extensión (.jsp) configurado en WebConfig.java
        return theme + "/home";
    }

    @RequestMapping("/create-theme")
    public ModelAndView theme(@RequestParam String theme) {
        this.theme = theme;
        return new ModelAndView(theme + "/home", "themes", THEMES);
    }

    @RequestMapping(value = "/greeting")
    public String greeting(@CookieValue("JSESSIONID") Cookie cookie, HttpServletRequest request, Model model) {
        model.addAttribute("stringList", Arrays.asList("uno", "dos", "tres"));
        model.addAttribute("cookie", cookie.getName());
        model.addAttribute("ip", request.getRemoteAddr());
        return theme + "/greeting";
    }

    @RequestMapping("/court-list")
    public ModelAndView listCourts(Model model) {
        ModelAndView modelAndView = new ModelAndView(themeChoose + "/courtList");
        modelAndView.addObject("courtList", courtController.showCourts());
        return modelAndView;
    }

    @RequestMapping(value = "/create-court", method = RequestMethod.GET)
    public String createCourt(Model model) {
        model.addAttribute("court", new CourtState(courtController.nextCourtId(), true));
        return themeChoose + "/createCourt";
    }

    @RequestMapping(value = "/create-court", method = RequestMethod.POST)
    public String createCourtSubmit(@Valid CourtState court, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (courtController.createCourt(court.getCourtId())) {
                model.addAttribute("id", court.getCourtId());
                return themeChoose + "/registrationSuccess";
            } else {
                bindingResult.rejectValue("id", "error.court", "Court ya existente");
            }
        }
        return themeChoose + "/createCourt";
    }

    @RequestMapping(value = {"/delete-court/{id}"})
    public String deleteCourt(@PathVariable int id, Model model) {
        courtController.changeCourtActivation(id, false);
        model.addAttribute("courtList", courtController.showCourts());
        return themeChoose + "/courtList";
    }

}
