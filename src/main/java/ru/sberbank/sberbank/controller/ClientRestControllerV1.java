package ru.sberbank.sberbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.service.SberAccountServiceImpl;
import ru.sberbank.sberbank.util.GeneralRestController;
import ru.sberbank.sberbank.util.GeneralService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/clients")
public class ClientRestControllerV1 extends GeneralRestController<ClientRestControllerV1, GeneralService<SberClientEntity>, SberClientEntity> {

    @Autowired
    private final GeneralService<SberAccountEntity> generalSberAccountService;

    public ClientRestControllerV1(GeneralService<SberClientEntity> m_iTservice, GeneralService<SberAccountEntity> generalSberAccountService) {
        super(m_iTservice,ClientRestControllerV1.class);
        this.generalSberAccountService = generalSberAccountService;
    }

    @GetMapping("/{clientId:[+]?[\\d]+}/accounts")
    @Operation(summary = "Получить все Счета Клиента")
    public ResponseEntity<CollectionModel<SberAccountEntity>> getAllByClientId(@PathVariable Integer clientId){
        SberAccountServiceImpl sberAccountService = (SberAccountServiceImpl) generalSberAccountService;
        List<SberAccountEntity> accountsByClientId = sberAccountService.getAccountsByClientId(clientId);
        return new ResponseEntity(accountsByClientId, HttpStatus.OK);
    }
}
