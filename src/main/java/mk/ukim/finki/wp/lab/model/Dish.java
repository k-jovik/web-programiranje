package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Dish {
    private Long id;
    private String dishId;
    private String name;
    private String cuisine;
    private int preparationTime;

    private Chef chef;

    public Dish(String dishId, String name, String cuisine, int preparationTime) {
        this.id = (long) (Math.random() * 1000);
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.chef = null;
    }

    public Dish(Long id, String dishId, String name, String cuisine, int preparationTime, Chef chef) {
        this.id = id;
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.chef = chef;
    }
}
