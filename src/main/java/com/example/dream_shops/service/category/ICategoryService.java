package com.example.dream_shops.service.category;

import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.service.product.IProductService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);

}
