package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class DataHolder {
    public static List<Chef> chefs = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();
    @PostConstruct
    public void init() {
        chefs.add(new Chef(0L, "Alice", "Waters", "Innovative farm-to-table chef.", new ArrayList<>(),new ArrayList<>()));
        chefs.add(new Chef(1L, "Gordon", "Ramsey", "A really angry chef.", new ArrayList<>(), new ArrayList<>()));
        chefs.add(new Chef(2L, "Jamie", "Oliver", "Famous for simple and healthy recipes.", new ArrayList<>(), new ArrayList<>()));
        chefs.add(new Chef(3L, "Nigella", "Lawson", "Comfort food and desserts expert.", new ArrayList<>(), new ArrayList<>()));
        chefs.add(new Chef(4L, "Thomas", "Keller", "Michelin-star fine dining specialist.", new ArrayList<>(), new ArrayList<>()));
        dishes.add(new Dish("1", "Pasta Primavera", "Italian", 12));
        dishes.add(new Dish("2", "Beef Bourguignon", "French", 22));
        dishes.add(new Dish("3", "Chicken Satay", "Indonesian", 14));
        dishes.add(new Dish("4", "Margherita Pizza", "Italian", 10));
        dishes.add(new Dish("5", "Chocolate Lava Cake", "Dessert", 8));

    }

}
