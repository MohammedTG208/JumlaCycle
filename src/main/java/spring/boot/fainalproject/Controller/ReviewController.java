package spring.boot.fainalproject.Controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.Review;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.ReviewService;
// محمد الغامدي

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get-all")
    public ResponseEntity getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.findAllReviewsByOrderId());
    }


    @PostMapping("/add-review/{orderId}")
    public ResponseEntity addReview(@PathVariable Integer orderId, @Valid @RequestBody Review review, @AuthenticationPrincipal User user) {
        reviewService.addReviewForProduct(orderId,review, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("review added successfully"));
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity updateReview(@Valid @RequestBody Review review, @AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        reviewService.updateReviewForProduct(reviewId,review, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("update successful"));
    }

    @DeleteMapping("/delete/{reviewId}/{userId}")
    public ResponseEntity deleteReview(@PathVariable Integer userId,@PathVariable Integer reviewId,@AuthenticationPrincipal User user) {
        reviewService.deleteReviewForProduct(user.getId(), reviewId,userId);
        return ResponseEntity.status(200).body(new ApiResponse("delete successful"));
    }

}
