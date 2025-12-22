package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByChefFirstNameContainingIgnoreCase(String text);

    List<Dish> findByChefFirstNameContainingIgnoreCaseOrChefLastNameContainingIgnoreCase(String firstName,String lastName);

    List<Dish> findByCuisineContainingIgnoreCase(String text);

    Dish findByDishId(String dishId);
    List<Dish> findAllByChef_Id(Long chefId);
}
