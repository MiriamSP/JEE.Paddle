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
import data.entities.Court;
import data.services.CourtService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class Presenter {
    
    @Autowired
    private CourtController courtController;
    
    @Autowired
    private CourtService courtService ;

    private static final List<String> THEMES = Arrays.asList("jsp", "bootstrap", "thymeleaf");
    
    @Autowired
    private ServletContext servletContext;

    
    private String theme = THEMES.get(0);

    public Presenter() {
        courtService = new CourtService();      
    }

    // Se ejecuta siempre y antes. Añade un atributo al Model
    @ModelAttribute("now")
    public String now() {
        return new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss").format(new Date());
    } 

    @RequestMapping("/home")
    public String home(Model model) {
        
        model.addAttribute("themes", THEMES);
        //La vista resultante no lleva extensión (.jsp) configurado en WebConfig.java
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
       // ModelAndView modelAndView = new ModelAndView(theme + "/userList");
        ModelAndView modelAndView = new ModelAndView("jsp/courtList");
        modelAndView.addObject("courtList", courtService.findAll());
        return modelAndView;
    }

   /*
    @RequestMapping(value = "/court-list", method = RequestMethod.GET)
    public String listCourts(Model model){
        //model.addAttribute("courtsList", courtController.showCourts());
        System.out.println("PRESENTER: " + courtService.findAll());
        model.addAttribute("courtsList", courtService.findAll());
        return "jsp/courtList";
    }
    */
    @RequestMapping(value ="/create-court", method = RequestMethod.GET)
    public String createCourt(Model model){
        //model.addAttribute("court", new CourtState(courtController.nextCourtId(), true));
        //System.out.println("PRESENTER: " + courtService.findAll());
        //System.out.println("PRESENTER: " + courtService.generateId());
        Court court = new Court(courtService.generateId());
        System.out.println("PRESENTER - new court: " + court.getId());
        model.addAttribute("court", court);
        //model.addAttribute("court", new Court(courtService.generateId()));
        return "jsp/createCourt";
        // return theme + "/createUser";
    }
        
   
    @RequestMapping(value = "/create-court", method = RequestMethod.POST)
    public String createCourtSubmit(@Valid Court court, BindingResult bindingResult, Model model) {
        System.out.println("PRESENTER - bindingResult : " + bindingResult.toString());
        System.out.println("PRESENTER - model  : " + model.toString());

        if (!bindingResult.hasErrors()) {
            System.out.println("PRESENTER - save: " + court.getId());

            if (courtService.save(court)) {
                model.addAttribute("id", court.getId());
                return //theme + 
                        "jsp/registrationSuccess";
            } else {
                bindingResult.rejectValue("id", "error.court", "Court ya existente");
            }
        }
        return //theme + 
                "jsp/createCourt";
    }
    
    
    
    /*
    
    @RequestMapping("/create-court")
    public ModelAndView createCourtmsp(@RequestParam String theme) {
        this.theme = theme;
        return new ModelAndView(theme + "/home", "themes", THEMES);
    }
    */

    
    
   

}
