package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error, Model model){
        if(error != null){
            model.addAttribute("error", error);
        }
        model.addAttribute("dishes",dishService.listDishes());
        return "listDishes";
    }


    @GetMapping("/add")
    public String showAddPage(Model model){
        model.addAttribute("dishes",dishService.listDishes());
        return "dish-form";
    }

    @PostMapping("/add")
    public String saveDish(@RequestParam String dishId, @RequestParam String name, @RequestParam String cuisine,
                           @RequestParam int preparationTime){
        this.dishService.create(dishId, name, cuisine, preparationTime);
        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id, @RequestParam String dishId, @RequestParam String name,
                           @RequestParam String cuisine, @RequestParam int preparationTime){
        this.dishService.update(id, dishId, name, cuisine, preparationTime);
        return "redirect:/dishes";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model){
        Dish dishToEdit = this.dishService.findById(id);
        if (dishToEdit != null){
            model.addAttribute("dish", dishToEdit);
            return "dish-form";
        }
        return "redirect:/dishes?error=DishNotFound";
    }
}
