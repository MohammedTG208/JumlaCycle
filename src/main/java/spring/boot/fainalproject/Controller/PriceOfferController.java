package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.PriceOfferService;

import java.util.List;
// فهد المسلم
@RestController
@RequestMapping("/api/v1/price-offer")
@RequiredArgsConstructor
public class PriceOfferController {
    private final PriceOfferService priceOfferService;

    @GetMapping("/get-all-price-offers")
    public ResponseEntity getAllPriceOffers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(priceOfferService.getAllPriceOffers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getPriceOfferById(@AuthenticationPrincipal User user ,@PathVariable Integer id) {
        return ResponseEntity.status(200).body(priceOfferService.getPriceOfferById(user.getId() , id));
    }

    @PostMapping("/create")
    public ResponseEntity createPriceOffer(@AuthenticationPrincipal User user,
                                           @RequestParam Integer recycleId,

                                           @RequestParam Double priceOffer) {
        priceOfferService.createPriceOffer(user.getId(), recycleId, priceOffer);
        return ResponseEntity.status(200).body(new ApiResponse("PriceOffer created successfully"));
    }
    @PutMapping("/approve-priceOffer/{priceOfferId}")
    public ResponseEntity approvePriceOffer(@AuthenticationPrincipal User user ,@PathVariable Integer priceOfferId) {
        priceOfferService.approvePriceOffer(user.getId(), priceOfferId);
        return ResponseEntity.status(200).body(new ApiResponse("PriceOffer approved successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updatePriceOffer(@AuthenticationPrincipal User user,
                                           @RequestParam Integer priceOfferId,
                                           @RequestParam Double price) {
        priceOfferService.updatePriceOffer(priceOfferId, user.getId(), price);
        return ResponseEntity.status(200).body(new ApiResponse("PriceOffer updated successfully"));
    }

    @DeleteMapping("/delete/{priceOfferId}")
    public ResponseEntity deletePriceOffer(@AuthenticationPrincipal User user,
                                           @PathVariable Integer priceOfferId
    ) {
        priceOfferService.cancelPriceOffer( user.getId(),priceOfferId);
        return ResponseEntity.status(200).body(new ApiResponse("PriceOffer deleted successfully"));
    }

    @GetMapping("/approved-offers/{supplierId}")
    public ResponseEntity<List<PriceOffer>> getApprovedOffersBySupplier(@AuthenticationPrincipal User user,@PathVariable Integer supplierId) {
        List<PriceOffer> approvedOffers = priceOfferService.getApprovedOffersBySupplier(user.getId(),supplierId);
        return ResponseEntity.status(200).body(approvedOffers);
    }
    @GetMapping("/pending-offers/facility/{facilityId}")
    public ResponseEntity<List<PriceOffer>> getPendingOffersByFacility(@AuthenticationPrincipal User user , @PathVariable Integer facilityId) {
        List<PriceOffer> pendingOffers = priceOfferService.getPendingOffersByFacility(user.getId(),facilityId);
        return ResponseEntity.status(200).body(pendingOffers);
    }

    @PutMapping("/rejected-Offer/{priceOfferId}")
    public ResponseEntity rejectOffer(@AuthenticationPrincipal User user,@PathVariable Integer priceOfferId) {
        priceOfferService.rejectedOffer(user.getId(), priceOfferId);
        return ResponseEntity.status(200).body(new ApiResponse("PriceOffer rejected successfully"));

    }

}
