package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.DTO.FacilityDTO;
import spring.boot.fainalproject.Model.FacilityRequest;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.FacilityService;

import java.util.List;
// فاطمة العبدي
@RestController
@RequestMapping("/api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("/get-all-facility")
    public ResponseEntity getAllFacility(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(facilityService.getAllFacilities());
    }

    @PostMapping("/register")
    public ResponseEntity registerFacility(@Valid @RequestBody FacilityDTO facilityDTO) {
        facilityService.registerFacility(facilityDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Facility registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateFacility(@AuthenticationPrincipal User user, @Valid @RequestBody FacilityDTO facilityDTO) {
        facilityService.updateFacility(user.getId(), facilityDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Facility updated successfully"));

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteFacility(@AuthenticationPrincipal User user) {
        facilityService.deleteFacility(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
    @PutMapping("/accept/{facilityRequestId}/{offerId}")
    public ResponseEntity acceptOffer(@AuthenticationPrincipal User user,
                                      @PathVariable Integer facilityRequestId,
                                      @PathVariable Integer offerId
    ) {

        facilityService.acceptOffer(user.getId(), facilityRequestId, offerId);
        return ResponseEntity.status(200).body(new ApiResponse("Offer accepted successfully"));
    }
    @PutMapping("/reject/{facilityRequestId}/{offerId}")
    public ResponseEntity<ApiResponse> rejectOffer(@AuthenticationPrincipal User user,
                                                   @PathVariable Integer facilityRequestId,
                                                   @PathVariable Integer offerId) {
        facilityService.rejectOffer(user.getId(), facilityRequestId, offerId);
        return ResponseEntity.status(200).body(new ApiResponse("Offer rejected successfully"));
    }

    @GetMapping("/requests")
    public ResponseEntity getAllRequestsMadeByFacility(@AuthenticationPrincipal User user) {
        List<FacilityRequest> requests = facilityService.getAllRequestsMadeByFacility(user.getId());
        return ResponseEntity.status(200).body(requests);
    }

    @GetMapping("/popular-recycle-products")
    public ResponseEntity getPopularRecycledProducts() {
        List<String> popularProducts = facilityService.getPopularRecycledProducts();
        return ResponseEntity.status(200).body(popularProducts);
    }
    @GetMapping("/get-recycle-request")
    public ResponseEntity recycleRequest(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(facilityService.getFacilityRecycleANDRequest(user.getId()));
    }
}
