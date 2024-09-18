package spring.boot.fainalproject.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.*;
import spring.boot.fainalproject.Repository.*;

import java.util.List;
// محمد الغامدي

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final OrderRepository orderRepository;

    private final FacilityRepository facilityRepository;

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Review> findAllReviewsByOrderId() {
        return reviewRepository.findAll();
    }

    public void addReviewForProduct(Integer orderId, Review review, Integer userId) {
        Customer customer = customerRepository.findCustomerById(userId);
        Order order = orderRepository.findOrderById(orderId);
        Facility facility = facilityRepository.findFacilityById(userId);

        if (order == null) {
            throw new ApiException("Order not found to add review for product");
        }

        if (order.getOrderStatus().equals("Shipped")) {
            if (order.getCustomer_orders() != null && order.getCustomer_orders().getId() == customer.getId()) {
                review.setCustomer(customer);
            }

            if (order.getFacility_orders() != null && order.getFacility_orders().getId() == facility.getId()) {
                review.setFacility(facility);
            }

            reviewRepository.save(review);
        } else {
            throw new ApiException("Your order must be shipped to add a review");
        }
    }

    public void updateReviewForProduct(Integer reviewId, Review review, Integer userId) {
        User user=authRepository.findUserById(userId);
        Review oldReview=reviewRepository.findReviewById(reviewId);

        if (oldReview==null) {
            throw new ApiException("Review not found to update");
        }

        if (review.getCustomer().getId()==user.getId()) {
            oldReview.setCustomer(customerRepository.findCustomerById(userId));
        }

        if (review.getFacility().getId()==user.getId()) {
            oldReview.setFacility(facilityRepository.findFacilityById(userId));
        }

        oldReview.setDescription(review.getDescription());
        oldReview.setRate(review.getRate());
        reviewRepository.save(oldReview);

    }


    public void deleteReviewForProduct(Integer adminId, Integer reviewId, Integer userId) {
        Review review= reviewRepository.findReviewById(reviewId);
        if (review.getCustomer().getId()==userId){
            reviewRepository.deleteReviewByCustomerId(userId);
        } else if (review.getFacility().getId() == userId) {
            reviewRepository.deleteReviewByFacilityId(userId);
        }else {
            throw new ApiException("Failed to delete review");
        }
    }
}