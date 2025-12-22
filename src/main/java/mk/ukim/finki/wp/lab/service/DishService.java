package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;

import java.util.List;

public interface DishService {
    List<Dish> listDishes();
    Dish findByDishId(String dishId);
    Dish findById(Long id);
    Dish create(String dishId, String name, String cuisine, int preparationTime);
    Dish create (Long id, String name, String cuisine, int preparationTime, Chef chef);
    Dish update(Long id, String dishId, String name, String cuisine, int preparationTime, Long chefId);
    void delete(Long id);
    List<Dish> listDishesSortedByChef();
    List<Dish> filterByMinPrepTime(int minPrepTime);
    List<Dish> filterByChefName(String chefFirstName, String chefLastName);
    List<Dish> filterByCuisine(String cuisine);

}
