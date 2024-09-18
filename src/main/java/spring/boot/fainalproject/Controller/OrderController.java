package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.Order;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.OrderService;
// محمد الغامدي

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get-all")
    public ResponseEntity getAllOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }
    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity findOrderByUserId(@AuthenticationPrincipal User user,@PathVariable Integer orderId) {
        return ResponseEntity.status(200).body(orderService.getOrderById( user.getId(), orderId));
    }

    @GetMapping("/get-all-by-userId")
    public ResponseEntity findAllByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getAllOrder(user.getId()));
    }

    @PostMapping("/add-order/{productId}")
    public ResponseEntity addOrder( @AuthenticationPrincipal User user ,@Valid @RequestBody Order order,@PathVariable Integer productId) {
        orderService.addNewOrder(user.getId() , order , productId);
        return ResponseEntity.status(200).body(new ApiResponse("order added successfully"));
    }

    @PutMapping("/update-by-userId/{orderId}")
    public ResponseEntity updateOrderById(@AuthenticationPrincipal User user , @Valid @RequestBody Order order,@PathVariable Integer orderId) {
        orderService.updateOrder(order, orderId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("order updated successfully"));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity deleteOrderById(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.deleteOrder(orderId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("order deleted successfully"));
    }
    @PutMapping("/supplier-shipping-order/{orderId}")
    public ResponseEntity supplierShippingOrder(@AuthenticationPrincipal User user,@PathVariable Integer orderId) {
        orderService.SupplierShippedOrder(user.getId(), orderId);
        return ResponseEntity.status(200).body(new ApiResponse("supplier shipped order successfully"));
    }

    @GetMapping("/recent-order-supplier")
    public ResponseEntity recentOrderSupplier(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getTodaysOrdersForSupplier(user.getId()));
    }
}

