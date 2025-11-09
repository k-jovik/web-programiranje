package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;


@WebServlet (name="ReviewServlet", urlPatterns =  "/chefReview")
public class ChefReviewServlet extends HttpServlet {
    TemplateEngine templateEngine;
    ChefService chefService;
    public ChefReviewServlet(TemplateEngine templateEngine, ChefService chefService) {
        this.templateEngine = templateEngine;
        this.chefService = chefService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        long id = -1L;

        try{
            id = Long.parseLong(req.getParameter("chefId"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String reviewChefs = req.getParameter("reviewChefs");
        Chef chef = chefService.addChefReview(id,reviewChefs);

        context.setVariable("chef", chef);
        context.setVariable("reviews", chef.getReviews());

        templateEngine.process("chefReviews.html",context,resp.getWriter());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chefReview = req.getParameter("reviewChefs");
        long id = -1L;
        try{
            id = Long.parseLong(req.getParameter("chefId"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Chef chef = chefService.findById(id);
        String chefReviews = req.getParameter("reviewChefs");

        resp.sendRedirect("/chefReview?chefId="+chef.getId()+"&reviewChefs="+chefReviews);

    }
}
