package spring.boot.fainalproject.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Facility;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Repository.FacilityRepository;
import spring.boot.fainalproject.Repository.PriceOfferRepository;
import spring.boot.fainalproject.Repository.RecyclingRequestRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;
// فهد المسلم
@Service
@RequiredArgsConstructor
public class RecyclingRequestService {

    private final RecyclingRequestRepository recyclingRequestRepository;
    private final FacilityRepository facilityRepository;
    private final SupplierRepository supplierRepository;
    private final PriceOfferRepository priceOfferRepository;

    // Get all recycling requests
    public List<RecyclingRequest> getAllRecyclingRequests(Integer id) {
        return recyclingRequestRepository.findAll();
    }

    // Get a specific recycling request by ID
    public RecyclingRequest getRecyclingRequestById(Integer supplier_id ,Integer id) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestById(id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }
        return recyclingRequest;
    }

    // Add a new recycling request
    public void addRecyclingRequest(Integer facilityId,RecyclingRequest recyclingRequest) {
        Facility facility = facilityRepository.findFacilityById(facilityId);
        if (facility == null ) {
            throw new ApiException("Both Facility and Supplier do not exist");
        }

        recyclingRequest.setFacility_recycle(facility);

        recyclingRequestRepository.save(recyclingRequest);

        assignRecyclingRequestToSuppliers(recyclingRequest);


    }
    private void assignRecyclingRequestToSuppliers(RecyclingRequest recyclingRequest) {
        List<Supplier> suppliers = supplierRepository.findAll();  // Fetch all suppliers

        for (Supplier supplier : suppliers) {
            // Add the recycling request to each supplier's recyclingRequests set
            supplier.getRecyclingRequests().add(recyclingRequest);

            // Set the supplier on the recycling request side
            recyclingRequest.setSupplier_recycle(supplier);

            // Save both the supplier and recycling request
            supplierRepository.save(supplier);
            recyclingRequestRepository.save(recyclingRequest);
        }
    }

    // Update an existing recycling request
    public void updateRecyclingRequest(Integer id, RecyclingRequest updatedRequest, Integer facilityId, Integer supplierId) {
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestById(id);
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }

        Facility facility = facilityRepository.findFacilityById(facilityId);
        Supplier supplier = supplierRepository.findSupplierById(supplierId);

        if (facility == null ) {
            throw new ApiException(" Facility do not exist");
        }
        if (supplier == null ) {
            throw new ApiException("Supplier do not exist");
        }

        recyclingRequest.setProductName(updatedRequest.getProductName());
        recyclingRequest.setQuantity(updatedRequest.getQuantity());
        recyclingRequest.setDescription(updatedRequest.getDescription());

        if (facility != null) {
            recyclingRequest.setFacility_recycle(facility);
        }

        if (supplier != null) {
            recyclingRequest.setSupplier_recycle(supplier);
        }

        recyclingRequestRepository.save(recyclingRequest);
    }

    // Delete a recycling request
    public void deleteRecyclingRequest(Integer facility_id ,Integer id) {
//        Facility facility = facilityRepository.findFacilityById(facility_id);
//        if (facility == null){
//            throw new ApiException("Facility not found");
//        }
        RecyclingRequest recyclingRequest = recyclingRequestRepository.findRecyclingRequestById(id);
//        Facility facility = recyclingRequest.getFacility_recycle();
//        if (facility.getId() != facility_id ) {
//            throw new ApiException("You are not authorized to cancel this offer");
//        }
        if (recyclingRequest == null) {
            throw new ApiException("Recycling request not found");
        }
        recyclingRequestRepository.delete(recyclingRequest);
    }
    // This endpoint returns recycling requests that have no price offers yet.
    public List<RecyclingRequest> getRecyclingRequestsWithNoPriceOffers(Integer userId) {
        return recyclingRequestRepository.findRecyclingRequestsWithNoPriceOffers();
    }


}
