package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.farm_animals.FarmAnimalCreateRequest;
import com.system.accounting.model.dto.farm_animals.FarmAnimalsResponse;
import com.system.accounting.service.farm_animals.FarmAnimalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farm_animals")
@RequiredArgsConstructor
@Api("Методы для работы с хозяйственными животными")
public class FarmAnimalsController {

    private final FarmAnimalService farmAnimalService;

    @PostMapping("/create")
    @ApiOperation("Создание нового вида хозяйственных животных")
    public BaseResponse<?> create(@RequestBody FarmAnimalCreateRequest request) {
        farmAnimalService.create(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Возвращает все виды хозяйственных животных")
    public BaseResponse<FarmAnimalsResponse> getFarmAnimals() {
        return new BaseResponse<>(farmAnimalService.getFarmAnimals());
    }
}
