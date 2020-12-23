package com.system.accounting.service.land_category;

import com.system.accounting.model.dto.land_category.LandCategoriesResponse;
import com.system.accounting.model.dto.land_category.LandCategoryCreateRequest;
import com.system.accounting.model.entity.LandCategoryEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.LandCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LandCategoryService {

    private final LandCategoryRepository landCategoryRepository;
    private final EmployeeRepository employeeRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public void create(LandCategoryCreateRequest request) {
        LandCategoryEntity entity = new LandCategoryEntity();
        entity.setName(request.getName());
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        landCategoryRepository.save(entity);
    }

    @Transactional
    public LandCategoriesResponse getCategories() {
        return new LandCategoriesResponse(landCategoryRepository.findAll());
    }
}
