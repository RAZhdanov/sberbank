package ru.sberbank.sberbank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.sberbank.entity.SberRawTransactionEntity;
import ru.sberbank.sberbank.entity.SberUserEntity;
import ru.sberbank.sberbank.util.GeneralRestController;
import ru.sberbank.sberbank.util.GeneralService;

@RestController
@RequestMapping(value = "/api/v1/transactions")
public class RawTransactionRestControllerV1 extends GeneralRestController<RawTransactionRestControllerV1, GeneralService<SberRawTransactionEntity>, SberRawTransactionEntity> {
    public RawTransactionRestControllerV1(GeneralService<SberRawTransactionEntity> m_iTservice) {
        super(m_iTservice,RawTransactionRestControllerV1.class);
    }
}
