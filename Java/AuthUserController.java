import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class AuthUserController {

    @Autowired
    private UserService userService;

    @Value("${services.api.base.url}")
    private String servicesApiBaseUrl;

    @Value("${login.token}")
    private String loginToken;

    @RequestMapping("/auth_user")
    public ModelAndView authUser(@RequestParam(name = "services_token", required = false) String servicesToken) {
        if (servicesToken == null) {
            return new ModelAndView("redirect:/index");
        }

        String endpoint = servicesApiBaseUrl + "/api/authorization/" + servicesToken;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(URI.create(endpoint), HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseData = response.getBody();
            String email = (String) responseData.get("internal_email_address");

            // Rest of the code to handle database operations and user creation...
            // For example, use JPA or Hibernate to interact with the database.
            // The handling of user creation and other database operations will vary
            // depending on your application's architecture and ORM choice.

            try {
                User user = userService.getUserByEmail(email);
                if (user == null) {
                    // Assuming the user creation part, you need to create a new user.
                    // For example:
                    // user = new User("username_" + email.split("@")[0] + "_" + Math.round(LocalDateTime.now().getSecond()), email, "your_random_password");
                    // userService.createUser(user);
                }

                // Assuming the user creation part, you need to perform the login operation.
                // For example:
                // performLoginOperation(request, user);
                
                ModelAndView modelAndView = new ModelAndView("redirect:/index");
                LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
                Duration duration = Duration.between(LocalDateTime.now(), midnight);
                modelAndView.addObject("services", "services_cookie");
                modelAndView.addObject("max_age", duration.getSeconds());
                return modelAndView;
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }

        return new ModelAndView("redirect:/index");
    }
}