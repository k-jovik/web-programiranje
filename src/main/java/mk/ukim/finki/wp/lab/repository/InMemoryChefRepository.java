//package mk.ukim.finki.wp.lab.repository;
//
//import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
//import mk.ukim.finki.wp.lab.model.Chef;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class InMemoryChefRepository implements ChefRepository {
//
//    @Override
//    public List<Chef> findAll() {
//        return DataHolder.chefs;
//    }
//
//    @Override
//    public Optional<Chef> findById(Long id) {
//        return DataHolder.chefs.stream().filter(chef -> chef.getId().equals(id)).findFirst();
//    }
//
//    @Override
//    public Chef save(Chef chef) {
//        if (chef == null || chef.getId() == null) {
//            return null;
//        }
//        DataHolder.chefs.removeIf(chef1 -> chef1.getId().equals(chef.getId()));
//        DataHolder.chefs.add(chef);
//        return chef;
//    }
//}
