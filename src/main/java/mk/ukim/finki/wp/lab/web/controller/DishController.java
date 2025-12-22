package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService,ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String chefSearchTerm,
                                @RequestParam(required = false) Integer minPrepTime,
                                @RequestParam(required = false) String cuisine,
                               Model model){
        if(error != null){
            model.addAttribute("error", error);
        }
        List<Chef> chefs = chefService.listChefs();
        model.addAttribute("chefs",chefs);

        // --- Sorting Logic ---
        List<Dish> dishes;
        if (minPrepTime != null) {
            // If the user provided a minimum preparation time, filter the dishes
            dishes = dishService.filterByMinPrepTime(minPrepTime);
        } else if (chefSearchTerm != null &&  !chefSearchTerm.isEmpty()) {
            // Existing sort by chef logic
            String term = chefSearchTerm.trim();
            dishes = dishService.filterByChefName(term,term);
        } else if (cuisine != null && !cuisine.isEmpty()) {
            // Filter by cuisine
            dishes = dishService.filterByCuisine(cuisine);
        }else {
            // Default list
            dishes = dishService.listDishes();
        }

        model.addAttribute("dishes",dishes);
        model.addAttribute("minPrepTime", minPrepTime);
        model.addAttribute("chefSearchTerm", chefSearchTerm);
        return "listDishes";
    }


    @GetMapping("/add")
    public String showAddPage(Model model){
        model.addAttribute("dishes",dishService.listDishes());
        List<Chef> chefs = chefService.listChefs();
        model.addAttribute("chefs",chefs);
        return "dish-form";
    }

    // АДАПТИРАН DishController (најдобар начин)
    @PostMapping("/add")
    public String saveDish(@RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam(required = false) Long chefId){

        // 1. Креирајте го јадењето БЕЗ поврзување со готвачот
        Dish newDish = this.dishService.create(dishId, name, cuisine, preparationTime);

        // 2. Ако е избран готвач, повикајте ја методата за поврзување
        if (chefId != null){
            // Оваа метода го наоѓа готвачот, го поврзува со јадењето и ги зачувува и двата објекти.
            chefService.addDishToChef(chefId, newDish.getDishId());
        }

        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id, @RequestParam String dishId, @RequestParam String name,
                           @RequestParam String cuisine, @RequestParam int preparationTime,@RequestParam Long chefId){
        this.dishService.update(id, dishId, name, cuisine, preparationTime,chefId);
        return "redirect:/dishes";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model){
        Dish dishToEdit = this.dishService.findById(id);
        if (dishToEdit != null){
            model.addAttribute("dish", dishToEdit);
            model.addAttribute("chefs", chefService.listChefs());
            return "dish-form";
        }
        return "redirect:/dishes?error=DishNotFound";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id){
        this.dishService.delete(id);
        return "redirect:/dishes";
    }

}
