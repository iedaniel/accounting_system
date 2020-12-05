package com.system.accounting.service.kozhuun;

import com.system.accounting.model.entity.EmployeeEntity;
import com.system.accounting.model.entity.KozhuunEntity;
import com.system.accounting.model.dto.kozhuun.KozhuunCreateRequest;
import com.system.accounting.model.dto.kozhuun.KozhuunsResponse;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.KozhuunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KozhuunService {

    private final KozhuunRepository kozhuunRepository;
    private final EmployeeRepository employeeRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public void create(KozhuunCreateRequest request) {
        String login = userInfoService.currentUserLogin();
        EmployeeEntity creator = employeeRepository.findByLogin(login);
        KozhuunEntity entity = new KozhuunEntity();
        entity.setName(request.getName());
        entity.setCreator(creator);
        kozhuunRepository.save(entity);
    }

    @Transactional
    public KozhuunsResponse getKozhuuns() {
        return new KozhuunsResponse(kozhuunRepository.findAll());
    }
}
