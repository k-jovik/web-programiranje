package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet (name = "dishServlet", urlPatterns = "/dish")
public class DishServlet extends HttpServlet {
    private final DishService dishService;
    private final ChefService chefService;
    private final TemplateEngine templateEngine;


    public DishServlet(DishService dishService, TemplateEngine templateEngine, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        String chefId = req.getParameter("chefId");
        if (chefId == null || chefId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"No chef selected");
            return;
        }
        long id = 0;
        try {
            id = Long.parseLong(chefId);
        }catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid chef id");
        }

        WebContext context = new WebContext(webExchange);

        Chef chef = chefService.findById(id);




        context.setVariable("dishes",dishService.listDishes());
        context.setVariable("chefId",chefId);
        context.setVariable("chefName",chef.getFirstName() + " " + chef.getLastName());

        templateEngine.process("dishesList.html",context,resp.getWriter());


    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chefId = req.getParameter("chefId");
        resp.sendRedirect("/dish?chefId="+chefId);
    }
}
