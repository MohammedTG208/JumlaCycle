package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Review;
// محمد الغامدي
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewById(Integer reviewId);

    @Query("DELETE FROM Review r WHERE r.id = ?1 AND r.order.id = ?2")
    void deleteReviewByIdAndOrderId(Integer reviewId, Integer orderId);

    @Query("select r from Review r where r.customer.id=?1")
    void deleteReviewByCustomerId(Integer customerId);
    @Query("select r from Review r where r.facility.id=?1")
    void deleteReviewByFacilityId(Integer facilityId);
}
