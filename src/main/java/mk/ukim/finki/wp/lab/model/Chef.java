package mk.ukim.finki.wp.lab.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data @Setter @AllArgsConstructor
public class Chef {
    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    private List<Dish> dishes;
}
