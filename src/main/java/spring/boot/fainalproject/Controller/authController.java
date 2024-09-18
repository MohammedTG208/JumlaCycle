package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.AuthService;

// فاطمة العبدي
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authController {
    private final AuthService authService;

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(authService.findAll());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @AuthenticationPrincipal User user1) {
        authService.updateUser(user, user1.getId());
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User user) {
        authService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }
}

