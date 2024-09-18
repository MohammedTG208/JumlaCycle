package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.RecyclingRequest;

import java.util.List;
//  فهد المسلم
@Repository
public interface RecyclingRequestRepository extends JpaRepository<RecyclingRequest, Integer> {
    RecyclingRequest findRecyclingRequestById(Integer id);

    @Query("SELECT r.productName FROM RecyclingRequest r GROUP BY r.productName " +
            "ORDER BY COUNT(r.productName) DESC")
    List<String> findMostRecycledProducts();

    @Query("SELECT rr FROM RecyclingRequest rr WHERE rr.priceOffers IS EMPTY")
    List<RecyclingRequest> findRecyclingRequestsWithNoPriceOffers();


}
