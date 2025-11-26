package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
