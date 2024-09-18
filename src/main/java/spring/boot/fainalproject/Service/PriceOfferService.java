package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.PriceOfferRepository;
import spring.boot.fainalproject.Repository.RecyclingRequestRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;
// فهد المسلم
@Service
@RequiredArgsConstructor
public class PriceOfferService {

    private final PriceOfferRepository priceOfferRepository;
    private final SupplierRepository supplierRepository;
    private final RecyclingRequestRepository recyclingRequestRepository;
    private final AuthRepository authRepository;

    public List<PriceOffer> getAllPriceOffers() {
        return priceOfferRepository.findAll();
    }

    public PriceOffer getPriceOfferById(Integer user_id , Integer id) {
        PriceOffer priceOffer = priceOfferRepository.findPriceOfferById(id);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }
        return priceOffer;
    }

    // Supplier creates a price offer
    public void createPriceOffer(Integer supplierId, Integer recyclingRequestId, double price) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ApiException("Supplier not found"));

        RecyclingRequest recyclingRequest = recyclingRequestRepository.findById(recyclingRequestId)
                .orElseThrow(() -> new ApiException("Recycling Request not found"));

        // Ensure the supplier has not already made an offer for this request
        if (priceOfferRepository.existsBySupplierAndRecyclingRequest(supplier, recyclingRequest)) {
            throw new ApiException("Price offer already submitted for this request.");
        }

        // Create and save the price offer
        PriceOffer priceOffer = new PriceOffer();
        priceOffer.setPrice(price);
        priceOffer.setStatus("PENDING");
        priceOffer.setSupplier(supplier);
        priceOffer.setRecyclingRequest(recyclingRequest);
        priceOfferRepository.save(priceOffer);
    }

    //     Facility approves a price offer
    public void approvePriceOffer(Integer facilityId, Integer priceOfferId) {
        PriceOffer priceOffer = priceOfferRepository.findById(priceOfferId)
                .orElseThrow(() -> new ApiException("Price Offer not found"));

        RecyclingRequest recyclingRequest = priceOffer.getRecyclingRequest();
        if (!recyclingRequest.getFacility_recycle().getId().equals(facilityId)) {
            throw new ApiException("Facility not authorized to approve this offer.");
        }

        priceOffer.setStatus("APPROVED");
        priceOfferRepository.save(priceOffer);

        // Reject other offers for this recycling request
        List<PriceOffer> otherOffers = priceOfferRepository.findByRecyclingRequestAndStatus(recyclingRequest, "PENDING");
        otherOffers.forEach(offer -> {
            offer.setStatus("REJECTED");
            priceOfferRepository.save(offer);
        });

        // Update supplier's badge based on approved price offers
        updateSupplierBadge(priceOffer.getSupplier());
    }

    // Badge update logic for supplier based on approved price offers
    private void updateSupplierBadge(Supplier supplier) {
        int approvedOffersCount = priceOfferRepository.countBySupplier(supplier);

        if (approvedOffersCount >= 6) {
            supplier.setBadge("GOLD");
        } else if (approvedOffersCount >= 4) {
            supplier.setBadge("SILVER");
        } else if (approvedOffersCount >= 2) {
            supplier.setBadge("BRONZE");
        } else {
            supplier.setBadge("IRON");
        }

        supplierRepository.save(supplier);
    }

    public void updatePriceOffer(Integer supplierId, Integer priceOfferId, double price) {
        PriceOffer priceOffer = priceOfferRepository.findById(priceOfferId)
                .orElseThrow(() -> new ApiException("Price Offer not found"));

        if (!priceOffer.getSupplier().getId().equals(supplierId)) {
            throw new ApiException("You are not authorized to update this price offer.");
        }

        priceOffer.setPrice(price);
        priceOffer.setStatus("PENDING");
        priceOfferRepository.save(priceOffer);
    }

    //REJECTED OFFER
    public void rejectedOffer(Integer facilityId,Integer priceOfferId) {
        PriceOffer priceOffer =priceOfferRepository.findPriceOfferById(priceOfferId);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }

        RecyclingRequest recyclingRequest = priceOffer.getRecyclingRequest();
        if (!recyclingRequest.getFacility_recycle().getId().equals(facilityId)) {
            throw new ApiException("Facility not authorized to Rejected this offer.");
        }

        if (priceOffer.getStatus().equals("APPROVED")) {
            throw new ApiException("Price Offer already submitted for this request.");
        }
        priceOffer.setStatus("REJECTED");
        priceOfferRepository.save(priceOffer);
    }


    public void cancelPriceOffer(Integer supplierId, Integer priceOfferId) {
        PriceOffer priceOffer = priceOfferRepository.findById(priceOfferId)
                .orElseThrow(() -> new ApiException("Price Offer not found"));

        if (!priceOffer.getSupplier().getId().equals(supplierId)) {
            throw new ApiException("You are not authorized to cancel this price offer.");
        }

        priceOffer.setStatus("CANCELED");
        priceOfferRepository.save(priceOffer);
    }
    //This endpoint returns all price offers that a supplier has submitted and that have been approved.
    public List<PriceOffer> getApprovedOffersBySupplier(Integer user_id ,Integer supplierId) {
        User user = authRepository.findUserById(user_id);
        if (!user.getSupplier().getId().equals(supplierId)) {
            throw new ApiException("Supplier not authorized to get this endpoint.");
        }
        return priceOfferRepository.findApprovedOffersBySupplierId(supplierId);
    }
    //This returns all the pending price offers for recycling requests from a specific facility.
    public List<PriceOffer> getPendingOffersByFacility(Integer user_id ,Integer facilityId) {
        User user = authRepository.findUserById(user_id);
        if (!user.getFacility().getId().equals(facilityId)) {
            throw new ApiException("Facility not authorized to get this endpoint.");
        }
        return priceOfferRepository.findPendingOffersByFacilityId(facilityId);
    }

}