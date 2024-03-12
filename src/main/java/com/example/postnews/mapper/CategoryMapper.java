package com.example.postnews.mapper;

import com.example.postnews.entity.Category;
import com.example.postnews.web.request.UpsertCategoryRequest;
import com.example.postnews.web.response.CategoryFindAllResponse;
import com.example.postnews.web.response.CategoryResponse;
import com.example.postnews.web.response.list.CategoryListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PostMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    CategoryFindAllResponse categoryFindAllToResponse(Category category);

    List<CategoryResponse> categoryListToResponseList(List<Category> categories);

}