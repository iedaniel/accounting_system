package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.agriculture.AgricultureCreateRequest;
import com.system.accounting.model.dto.agriculture.AgriculturesResponse;
import com.system.accounting.service.agriculture.AgricultureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agriculture")
@RequiredArgsConstructor
@Api(tags = "Работа с видами сельскохозяйственных культур")
public class AgricultureController {

    private final AgricultureService agricultureService;

    @PostMapping("/create")
    @ApiOperation("Создать вид хозяйственной культуры")
    public BaseResponse<?> create(@RequestBody AgricultureCreateRequest request) {
        agricultureService.create(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Показать виды хозяйственных культур")
    public BaseResponse<AgriculturesResponse> getCultures() {
        return new BaseResponse<>(agricultureService.getCultures());
    }
}
