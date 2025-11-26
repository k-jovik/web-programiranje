package mk.ukim.finki.wp.lab.service;


import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChefServiceImpl implements ChefService {

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public ChefServiceImpl(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }


    @Override
    public List<Chef> listChefs() {
        return chefRepository.findAll();
    }

    @Override
    public Chef findById(Long id) {
        if (id == null) { return null;}
        return chefRepository.findById(id).orElse(null);
    }

    @Override
    public Chef addDishToChef(Long chefId, String dishId) {
        Chef chef = findById(chefId);
        Dish dish = dishRepository.findByDishId(dishId);
        if (chef == null || dish == null) { return null;}
        dish.setChef(chef);
        chef.getDishes().add(dish);
        chefRepository.save(chef);
        return chef;
    }

    @Override
    public Chef addChefReview(long chefId, String review) {
        Chef chef = findById(chefId);
        if (chef.getReviews() == null) { chef.setReviews(new ArrayList<>()); }

        if (review != null) {
            chef.getReviews().add(review);
        }
        return chef;
    }
}
