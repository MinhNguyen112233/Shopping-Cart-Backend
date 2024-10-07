package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Categories retrieved successfully!")
                    .data(categoryService.getAllCategories())
                    .build());
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Category added successfully!")
                    .data(categoryService.addCategory(name))
                    .build());
        }catch(Exception e){
            return ResponseEntity.status(CONFLICT).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Category retrieved successfully!")
                    .data(categoryService.getCategoryById(id))
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Category retrieved successfully!")
                    .data(categoryService.getCategoryByName(name))
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .data(null)
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Category retrieved successfully!")
                    .data(null)
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .data(null)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try{
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Category retrieved successfully!")
                    .data(categoryService.updateCategory(category, id))
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .data(null)
                    .message(e.getMessage())
                    .build());
        }
    }

}
