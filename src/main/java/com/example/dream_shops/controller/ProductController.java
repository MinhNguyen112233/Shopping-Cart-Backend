package com.example.dream_shops.controller;

import com.example.dream_shops.dto.ProductDto;
import com.example.dream_shops.exception.AlreadyExistsException;
import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts(); // <1>
        List<ProductDto> productDtos = productService.getConvertedProducts(products);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Products retrieved successfully!")
                .data(productDtos)
                .build());
    }

    @GetMapping("product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try{
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDto)
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        try{

            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product added successfully!")
                    .data(productService.addProduct(request))
                    .build());
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest
                                                             request, @PathVariable Long id){
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product updated successfully!")
                    .data(productService.updateProductById(request, id))
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProductById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product deleted successfully!")
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return (productService.getProductsByBrandAndName(brand,name).isEmpty())
                    ? ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDtos)
                    .build())
                    : ResponseEntity.status(NOT_FOUND).body(
                            ApiResponse.builder()
                    .message("Product not found!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try{
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return (productService.getProductsByCategoryAndBrand(category,brand).isEmpty())
                    ? ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDtos)
                    .build())
                    : ResponseEntity.status(NOT_FOUND).body(
                    ApiResponse.builder()
                            .message("Product not found!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try{
            List<Product> products = productService.getProductsByName(name); // <1>
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return (productService.getProductsByName(name).isEmpty())
                    ? ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDtos)
                    .build())
                    : ResponseEntity.status(NOT_FOUND).body(
                    ApiResponse.builder()
                            .message("Product not found!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try{
            List<Product> products = productService.getProductsByBrand(brand); // <1>
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return (productService.getProductsByBrand(brand).isEmpty())
                    ? ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDtos)
                    .build())
                    : ResponseEntity.status(NOT_FOUND).body(
                    ApiResponse.builder()
                            .message("Product not found!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try{
            List<Product> products = productService.getProductsByCategory(category); // <1>
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return (productService.getProductsByCategory(category).isEmpty())
                    ? ResponseEntity.ok(ApiResponse.builder()
                    .message("Product retrieved successfully!")
                    .data(productDtos)
                    .build())
                    : ResponseEntity.status(NOT_FOUND).body(
                    ApiResponse.builder()
                            .message("Product not found!").build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
