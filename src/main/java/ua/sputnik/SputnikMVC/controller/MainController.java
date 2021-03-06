package ua.sputnik.SputnikMVC.controller;

/**
 * @author Barma
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.sputnik.SputnikMVC.model.entity.Event;
import ua.sputnik.SputnikMVC.model.entity.User;
import ua.sputnik.SputnikMVC.service.EventService;
import ua.sputnik.SputnikMVC.service.TicketService;

@Controller
public class MainController {

    private EventService eventService;
    private TicketService ticketService;

    @Autowired
    public MainController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String greeting(
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {

        model.addAttribute("page", eventService.findAll(pageable));
        model.addAttribute("url", "");
        return "greeting";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("tickets", ticketService.findAllByUserId(user.getId()));
        return "main";
    }

}
