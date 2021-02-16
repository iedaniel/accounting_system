package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.land_type.LandTypeRequest;
import com.system.accounting.model.dto.land_type.LandTypesResponse;
import com.system.accounting.service.land_types.LandTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/land_types")
@RequiredArgsConstructor
@Api(tags = "Добавление/показ видов земель")
public class LandTypeController {

    private final LandTypeService landTypeService;

    @PostMapping("/create")
    @ApiOperation("Добавить вид земли")
    public BaseResponse<?> createLandType(@RequestBody LandTypeRequest request) {
        landTypeService.createLandType(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Показать виды земельных участков")
    public BaseResponse<LandTypesResponse> getLandTypes() {
        return new BaseResponse<>(landTypeService.getLandTypes());
    }
}
