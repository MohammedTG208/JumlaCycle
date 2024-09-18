package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.DTO.SupplierDTO;
import spring.boot.fainalproject.Model.Offer;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.SupplierService;

import java.util.List;
// فهد المسلم
@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/get-all-suppliers")
    public ResponseEntity getAllSuppliers(@AuthenticationPrincipal User user) {
       return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    @PostMapping("/register-supplier")
    public ResponseEntity registerSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        supplierService.registerSupplier(supplierDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successes add supplier"));
    }
    @PutMapping("/update-supplier")
    public ResponseEntity updateSupplier(@AuthenticationPrincipal User user, @Valid @RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(user.getId(), supplierDTO);
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    @DeleteMapping("/delete-supplier")
    public ResponseEntity deleteSupplier(@AuthenticationPrincipal User user ) {
        supplierService.deleteSupplier(user.getId());
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }
    // Accept a FacilityRequest
    @PutMapping("/accept-request/{facilityRequestId}")
    public ResponseEntity acceptFacilityRequest(@AuthenticationPrincipal User user,
                                                @PathVariable Integer facilityRequestId) {
        supplierService.acceptFacilityRequest(user.getId(), facilityRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Facility request accepted successfully"));
    }

    // Reject a FacilityRequest
    @PutMapping("/reject-request/{facilityRequestId}")
    public ResponseEntity rejectFacilityRequest(@AuthenticationPrincipal User user,
                                                @PathVariable Integer facilityRequestId) {
        supplierService.rejectFacilityRequest(user.getId(), facilityRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Facility request rejected successfully"));
    }
    @GetMapping("/offers")
    public ResponseEntity<List<Offer>> getSupplierOffers(@AuthenticationPrincipal User user) {
        List<Offer> offers = supplierService.getOffersBySupplierId(user.getId());
        return ResponseEntity.status(200).body(offers);
    }
    @GetMapping("/gold-suppliers")
    public ResponseEntity<List<Supplier>> getGoldBadgeSuppliers() {
        List<Supplier> goldSuppliers = supplierService.getAllGoldBadgeSuppliers();
        return ResponseEntity.status(200).body(goldSuppliers);
    }
    @GetMapping("/suppliers-by-badge/{badge}")
    public ResponseEntity<List<Supplier>> getSuppliersByBadge(@AuthenticationPrincipal User user, @PathVariable String badge) {
        List<Supplier> suppliers = supplierService.getSuppliersByBadge(badge);
        return ResponseEntity.status(200).body(suppliers);
    }
    @GetMapping("/get-my-price-offer")
    public ResponseEntity getAllPriceOfferMadeBySupplier(@AuthenticationPrincipal User user) {
        List<PriceOffer> priceOffers=supplierService.getAllPriceOfferMadeBySupplier(user.getId());
        return ResponseEntity.status(200).body(priceOffers);
    }
}
