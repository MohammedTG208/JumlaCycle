package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.Supplier;

import java.util.List;
//  فهد المسلم
@Repository
public interface PriceOfferRepository extends JpaRepository<PriceOffer, Integer> {
    PriceOffer findPriceOfferById(Integer id);

    boolean existsBySupplierAndRecyclingRequest(Supplier supplier, RecyclingRequest recyclingRequest);

    List<PriceOffer> findByRecyclingRequestAndStatus(RecyclingRequest recyclingRequest, String status);

    @Query("SELECT COUNT(po) FROM PriceOffer po WHERE po.supplier = :supplier AND po.status = 'APPROVED'")
    int countBySupplier(@Param("supplier") Supplier supplier);

    @Query("SELECT po FROM PriceOffer po WHERE po.supplier.id = :supplierId AND po.status = 'APPROVED'")
    List<PriceOffer> findApprovedOffersBySupplierId(@Param("supplierId") Integer supplierId);

    @Query("SELECT po FROM PriceOffer po WHERE po.recyclingRequest.facility_recycle.id = :facilityId AND po.status = 'PENDING'")
    List<PriceOffer> findPendingOffersByFacilityId(@Param("facilityId") Integer facilityId);

    List<PriceOffer> findBySupplier(Supplier supplier);


}
