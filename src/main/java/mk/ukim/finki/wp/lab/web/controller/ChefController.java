package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chefs")
public class ChefController {
    private final ChefService chefService;
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping
    public String chefs(Model model) {
        List<Chef> chefs;
        chefs = chefService.listChefs();
        model.addAttribute("chefs", chefs);
        return "listChefs";
    }

    @GetMapping("/add")
    public String showAddChefForm(Model model) {
        model.addAttribute("chefs", chefService.listChefs());
        return "chef-form";
    }

    @PostMapping("/add")
    public String saveChef(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio,
                           Model model) {
        Chef chef = chefService.create(firstName, lastName, bio);
        return "redirect:/chefs";
    }

    @PostMapping("/delete/{id}")
    public String deleteChef(@PathVariable Long id) {
        chefService.delete(id);
        return "redirect:/chefs";
    }
}
