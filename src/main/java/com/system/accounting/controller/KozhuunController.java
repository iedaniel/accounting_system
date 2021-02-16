package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.kozhuun.KozhuunCreateRequest;
import com.system.accounting.model.dto.kozhuun.KozhuunsResponse;
import com.system.accounting.service.kozhuun.KozhuunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kozhuuns")
@RequiredArgsConstructor
@Api(tags = "Работа с кожуунами")
public class KozhuunController {

    private final KozhuunService kozhuunService;

    @PostMapping("/create")
    @ApiOperation("Создать кожуун")
    public BaseResponse<?> create(@RequestBody KozhuunCreateRequest request) {
        kozhuunService.create(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Отобразить все кожууны")
    public BaseResponse<KozhuunsResponse> getKozhuuns() {
        return new BaseResponse<>(kozhuunService.getKozhuuns());
    }
}
