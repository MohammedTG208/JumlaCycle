package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.RecyclingRequestService;

import java.util.List;
// فهد المسلم

@RestController
@RequestMapping("/api/v1/recycling-request")
@RequiredArgsConstructor
public class RecyclingRequestController {

    private final RecyclingRequestService recyclingRequestService;

    @GetMapping("/get-all-recycling-requests")
    public ResponseEntity getAllRecyclingRequests(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(recyclingRequestService.getAllRecyclingRequests(user.getId()));
    }

    @GetMapping("/get-recycling-request-by-id/{id}")
    public ResponseEntity getRecyclingRequestById(@AuthenticationPrincipal User user ,@PathVariable Integer id) {
        return ResponseEntity.status(200).body(recyclingRequestService.getRecyclingRequestById(user.getId() , id));
    }

    @PostMapping("/add-recycling-request")
    public ResponseEntity addRecyclingRequest(@AuthenticationPrincipal User user,
                                              @Valid @RequestBody RecyclingRequest recyclingRequest
                                              ) {
        recyclingRequestService.addRecyclingRequest(user.getId() , recyclingRequest);
        return ResponseEntity.status(201).body(new ApiResponse("Recycling request added successfully"));
    }

    @PutMapping("/update-recycling-request/{id}")
    public ResponseEntity updateRecyclingRequest(@PathVariable Integer id,
                                                 @Valid @RequestBody RecyclingRequest recyclingRequest,
                                                 @AuthenticationPrincipal User user,
                                                 @RequestParam Integer supplierId) {
        recyclingRequestService.updateRecyclingRequest(id, recyclingRequest, user.getId(), supplierId);
        return ResponseEntity.status(200).body(new ApiResponse("Recycling request updated successfully"));
    }

    @DeleteMapping("/delete-recycling-request/{id}")
    public ResponseEntity deleteRecyclingRequest(@AuthenticationPrincipal User user ,@PathVariable Integer id) {
        recyclingRequestService.deleteRecyclingRequest(user.getId() , id);
        return ResponseEntity.status(200).body(new ApiResponse("Recycling request deleted successfully"));
    }
    @GetMapping("/no-price-offers")
    public ResponseEntity<List<RecyclingRequest>> getRecyclingRequestsWithNoPriceOffers(@AuthenticationPrincipal User user) {
        List<RecyclingRequest> requests = recyclingRequestService.getRecyclingRequestsWithNoPriceOffers(user.getId());
        return ResponseEntity.status(200).body(requests);
    }

}
