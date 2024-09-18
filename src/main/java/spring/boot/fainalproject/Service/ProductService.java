package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.ProductRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final AuthRepository authRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a specific product by ID
    public Product getProductById(Integer id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        return product;
    }

    // Add a new product by Supplier
    public void addProduct(Integer supplierId, Product product) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        product.setSupplier(supplier); // Set supplier to the product
        productRepository.save(product);
    }

    // Update an existing product
    public void updateProduct(Integer id, Product updatedProduct, Integer supplierId) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("Product not found");
        }

        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }

        product.setProductName(updatedProduct.getProductName());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setImgURL(updatedProduct.getImgURL());
        product.setSupplier(supplier);

        productRepository.save(product);
    }

    // Delete a product
    public void deleteProduct(Integer user_id , Integer id) {
        Supplier user = supplierRepository.findSupplierById(user_id);
        if (user == null) {
            throw new ApiException("Supplier not found");
        }
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        productRepository.delete(product);
    }

    //search for products by product name
    // extra 8
    public List<Product> searchProduct(String productName) {
        if (productRepository.findProductByProductName(productName).isEmpty()){
            throw new ApiException("Product not found");
        }else {
            return productRepository.findProductByProductName(productName);
        }
    }

    //get product by category
    //extra 10
    public List<Product> searchByCategory(String category) {
        if (productRepository.findProductByCategory(category).isEmpty()){
            throw new ApiException("Product by this category not found");
        }else {
            List<Product> products = productRepository.findProductByCategory(category);
            products.forEach(product -> product.setOrders(null));
            return products;
        }
    }

    // Method to get the best-selling product for a supplier
    //extra 9
    public List<Product> getBestSellingProductBySupplier(Integer supplierId) {
       Supplier supplier = supplierRepository.findSupplierById(supplierId);
        List<Product> products = productRepository.findBestSellingProductBySupplierId(supplierId);
        if (supplier == null){
            throw new ApiException("Supplier not found");
        }
            // Return the top product if available
            if (products.isEmpty()) {
                throw new ApiException("Supplier dont have best product yet");
            }else {
                return products;
            }
        }
    //Alert supplier if quantity less than 3
    //extra 13
    public List<Product> alertSupplier(Integer supplierId) {
        List<Product> checkQuantity = productRepository.findProductBySupplierID(supplierId);
        List<Product> lessThan =new ArrayList<>();
        for (Product product : checkQuantity) {
            if (product.getQuantity()<=3){
                lessThan.add(product);
            }
        }
        return lessThan;
    }

}