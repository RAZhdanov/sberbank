package ru.sberbank.sberbank.controller;

import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.sberbank.entity.SberUserEntity;
import ru.sberbank.sberbank.util.GeneralRestController;
import ru.sberbank.sberbank.util.GeneralService;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestControllerV1 extends GeneralRestController<UserRestControllerV1, GeneralService<SberUserEntity>, SberUserEntity> {

    public UserRestControllerV1(GeneralService<SberUserEntity> m_iTservice) {
        super(m_iTservice,UserRestControllerV1.class);
    }
}
