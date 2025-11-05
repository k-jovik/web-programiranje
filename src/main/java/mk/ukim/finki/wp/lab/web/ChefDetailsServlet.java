package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet (name = "ChefDetailsServlet ", urlPatterns = "/chefDetails")
public class ChefDetailsServlet extends HttpServlet {
    private final ChefService chefService;
    private final DishService dishService;
    private final TemplateEngine templateEngine;


    public ChefDetailsServlet(ChefService chefService,TemplateEngine templateEngine, DishService dishService) {
        this.chefService = chefService;
        this.dishService = dishService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);

        WebContext context = new WebContext(webExchange);
        String dishId = req.getParameter("dishId");


        long id = -1L;
        try{
            id = Long.parseLong(req.getParameter("chefId"));
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }


        Chef chef = chefService.findById(id);

        context.setVariable("chefName",chef.getFirstName() + ' ' + chef.getLastName());
        context.setVariable("bio",chef.getBio());
        context.setVariable("dishes",chef.getDishes());

        templateEngine.process("chefDetails.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long chefId = -1L;
        try {
            chefId = Long.parseLong(req.getParameter("chefId"));
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        String dishId = req.getParameter("dishId");
        Dish dish = dishService.findByDishId(dishId);
        Chef chef = chefService.findById(chefId);
        chefService.addDishToChef(chefId,dishId);
        resp.sendRedirect("/chefDetails?chefId="+chefId);

    }
}
