package com.example.postnews.web.controller;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Post;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.CategoryMapper;
import com.example.postnews.service.CategoryService;
import com.example.postnews.web.request.UpsertCategoryRequest;
import com.example.postnews.web.response.CategoryResponse;
import com.example.postnews.web.response.list.CategoryListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {

        Page<Category> categories = categoryService.findAll(pageNumber,pageSize);
        return ResponseEntity.ok(
                CategoryListResponse.builder()
                        .categoryResponseList(categories.stream().map(categoryMapper :: categoryFindAllToResponse).toList())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            List<Post> postList = category.getPosts();
            if (postList != null && !postList.isEmpty()) {
                for (Post post : postList) {
                    post.setCategory(null);
                    post.setComments(null);
                    post.setUser(null);
                }
            }
            return ResponseEntity.ok(categoryMapper.categoryToResponse(category));
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId,
                                                   UpsertCategoryRequest request) {
        Category updateCategory = categoryService.update(categoryMapper.requestToCategory(categoryId,request));
        if (updateCategory != null) {
            return ResponseEntity.ok(categoryMapper.categoryToResponse(updateCategory));
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", categoryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Category deleteCategory = categoryService.deleteById(id);
        if (deleteCategory != null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", id));
    }
}
