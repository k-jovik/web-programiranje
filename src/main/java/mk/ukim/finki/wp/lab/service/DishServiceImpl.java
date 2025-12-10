package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }


    @Override
    public Dish findByDishId(String dishId) {
        return dishRepository.findByDishId(dishId);
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String dishId, String name, String cuisine, int preparationTime) {
        Dish newDish = new Dish(dishId, name, cuisine, preparationTime);
        return dishRepository.save(newDish);
    }

    @Override
    public Dish create(Long id, String name, String cuisine, int preparationTime, Chef chef) {
        Dish newDish = new Dish(id,name, name, cuisine, preparationTime,chef);
        return dishRepository.save(newDish);
    }


    @Override
    public List<Dish> filterByMinPrepTime(int minPrepTime) {
        return this.dishRepository.findAll().stream()

                // FILTER: Keep only dishes where preparationTime is greater than or equal to minPrepTime (X)
                .filter(dish -> dish.getPreparationTime() >= minPrepTime)

                .collect(Collectors.toList());
    }


    // *** NEW METHOD FOR ORDERING/SORTING ***
    @Override
    public List<Dish> listDishesSortedByChef() {
        return this.dishRepository.findAll().stream()
                .sorted(Comparator.comparing(dish -> {
                    // Check for null chef (dishes not yet assigned) and use a default empty string
                    if (dish.getChef() == null) {
                        return "";
                    }
                    // Sort primarily by Chef's last name, then first name for consistency
                    return dish.getChef().getLastName() + dish.getChef().getFirstName();
                }))
                .collect(Collectors.toList());
    }

    @Override
    public Dish update(Long id, String dishId, String name, String cuisine, int preparationTime) {
        Dish existingDish = dishRepository.findById(id).orElse(null);
        if (existingDish != null) {
            existingDish.setDishId(dishId);
            existingDish.setName(name);
            existingDish.setCuisine(cuisine);
            existingDish.setPreparationTime(preparationTime);
        }
        return dishRepository.save(existingDish);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

}
