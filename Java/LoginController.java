import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("network/login");

        if (request.getMethod().equals("POST")) {
            // Attempt to sign user in
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = userService.authenticateUser(username, password);

            // Check if authentication successful
            if (user != null) {
                // Perform login operation
                // For example, set user details in the session or authentication context
                return new ModelAndView("redirect:/index");
            } else {
                modelAndView.addObject("message", "Invalid username and/or password.");
            }
        }

        // Check if request was sent from Services
        if (request.getParameter("services") != null) {
            String serviceName = request.getParameter("services");
            return authUser(request, serviceName);
        }

        return modelAndView;
    }

    private ModelAndView authUser(HttpServletRequest request, String serviceName) {
        // Handle the authentication for services
        // For example, redirect to a service-specific login page
        // Or set service-specific authentication details in the session
        return new ModelAndView("redirect:/services/" + serviceName);
    }
}
