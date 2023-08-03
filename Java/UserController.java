import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @GetMapping("/check-user-authentication")
    public ResponseEntity<?> checkUserAuthentication(HttpServletRequest request) {
        String servicesCookie = null;

        // Retrieve the "services" cookie from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("services")) {
                    servicesCookie = cookie.getValue();
                    break;
                }
            }
        }

        // If "services" cookie is not found, perform logout and return a redirect response
        if (servicesCookie == null) {
            // Perform logout operation, however, this will depend on your specific application's implementation.
            // For example:
            // logout(request);

            // Returning a redirect response
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/index").build();
        }

        // If "services" cookie is found, return a success JSON response
        return ResponseEntity.ok().body("{\"success\": true, \"message\": \"No services user\"}");
    }
}

