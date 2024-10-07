package com.example.dream_shops.service.product;

import com.example.dream_shops.dto.ProductDto;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProductById(ProductUpdateRequest request , Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    //map từ product -> productDto
    ProductDto convertToDto(Product product);
}
