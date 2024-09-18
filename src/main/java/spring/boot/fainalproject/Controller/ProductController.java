package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.API.ApiResponse;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.ProductService;
// فاطمة العبدي
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping("/get/all")
    public ResponseEntity getAllProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    // Get a product by ID
    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(productService.getProductById(id));
    }

    // Add a new product by a Supplier
    @PostMapping("/add")
    public ResponseEntity addProduct(@AuthenticationPrincipal User user, @Valid @RequestBody Product product) {
        productService.addProduct(user.getId(), product);
        return ResponseEntity.status(201).body(new ApiResponse("Product added successfully"));
    }

    // Update an existing product by Supplier
    @PutMapping("/update-product/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id,
                                        @AuthenticationPrincipal User user,
                                        @Valid @RequestBody Product productDetails) {
        productService.updateProduct(id, productDetails, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }

    // Delete a product
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity deleteProduct(@AuthenticationPrincipal User user, @PathVariable Integer id ) {
        productService.deleteProduct(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
    }

    //search product
    //this is new......
    @GetMapping("/search-product")
    public ResponseEntity searchProduct(@RequestParam String productName) {
        return ResponseEntity.status(200).body(productService.searchProduct(productName));
    }

    // get the best-selling product for a supplier
    //this new also......
    @GetMapping("/best-seller/{supplier_id}")
    public ResponseEntity getBestSellingProduct(@PathVariable Integer supplier_id) {
        return ResponseEntity.status(200).body(productService.getBestSellingProductBySupplier(supplier_id));
    }

    //search by category
    //new endpoint
    @GetMapping("/search-by-category/{category}")
    public ResponseEntity getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(productService.searchByCategory(category));
    }
    @GetMapping("/alertSupplier")
    public ResponseEntity alertSupplier(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.alertSupplier(user.getId()));
    }
}