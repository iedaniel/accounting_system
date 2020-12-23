package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.land_category.LandCategoriesResponse;
import com.system.accounting.model.dto.land_category.LandCategoryCreateRequest;
import com.system.accounting.service.land_category.LandCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/land_category")
@RequiredArgsConstructor
public class LandCategoryController {

    private final LandCategoryService landCategoryService;

    @PostMapping("/create")
    public BaseResponse<?> create(@RequestBody LandCategoryCreateRequest request) {
        landCategoryService.create(request);
        return new BaseResponse<>();
    }

    @GetMapping
    public BaseResponse<LandCategoriesResponse> getCategories() {
        return new BaseResponse<>(landCategoryService.getCategories());
    }
}
