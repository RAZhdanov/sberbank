package ru.sberbank.sberbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.sberbank.dto.TransferMoneyDto;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberRawTransactionEntity;
import ru.sberbank.sberbank.service.SberAccountServiceImpl;
import ru.sberbank.sberbank.service.SberRawTransactionServiceImpl;
import ru.sberbank.sberbank.util.GeneralRestController;
import ru.sberbank.sberbank.util.GeneralService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountRestControllerV1 extends GeneralRestController<AccountRestControllerV1, GeneralService<SberAccountEntity>, SberAccountEntity> {


    private final GeneralService<SberRawTransactionEntity> generalRawTransactionService;

    @Autowired
    public AccountRestControllerV1(GeneralService<SberAccountEntity> m_iTservice, GeneralService<SberRawTransactionEntity> generalRawTransactionService) {
        super(m_iTservice,AccountRestControllerV1.class);
        this.generalRawTransactionService = generalRawTransactionService;
    }

    @PostMapping("/transfer")
    @Operation(summary = "Перевести деньги со Счета A на Счет B")
    @Transactional
    public ResponseEntity<Collection<SberAccountEntity>> transferMoneyBetweenAccounts(@RequestBody TransferMoneyDto transferMoneyDto)
    {
        GeneralService<SberAccountEntity> tservice = getM_iTservice();
        Optional<SberAccountEntity> source = tservice.findById(transferMoneyDto.getSourceAccountId());
        Optional<SberAccountEntity> target = tservice.findById(transferMoneyDto.getTargetAccountId());
        Double quantity = transferMoneyDto.getQuantity();

        if(!source.isPresent() || !target.isPresent() || quantity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SberAccountEntity sberAccountEntitySource = source.get();
        SberAccountEntity sberAccountEntityTarget = target.get();

        if(sberAccountEntitySource.getCash() < quantity){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        sberAccountEntitySource.setCash(sberAccountEntitySource.getCash() - quantity);
        sberAccountEntityTarget.setCash(sberAccountEntityTarget.getCash() + quantity);

        Collection<SberAccountEntity> sberAccountEntities = new ArrayList<>();
        sberAccountEntities.add(sberAccountEntitySource);
        sberAccountEntities.add(sberAccountEntityTarget);
        Collection<SberAccountEntity> newSberAccountEntities = tservice.editAll(sberAccountEntities);

        Collection<SberRawTransactionEntity> sberRawTransactionEntities = new ArrayList<>();

        sberRawTransactionEntities.add(new SberRawTransactionEntity(quantity * -1, sberAccountEntitySource.getCash(), new Date(), sberAccountEntitySource));
        sberRawTransactionEntities.add(new SberRawTransactionEntity(quantity, sberAccountEntityTarget.getCash(), new Date(), sberAccountEntityTarget));

        generalRawTransactionService.editAll(sberRawTransactionEntities);

        return new ResponseEntity<>(newSberAccountEntities, HttpStatus.OK);
    }

    @GetMapping("/{accountId:[\\d]+}/transactions")
    @Operation(summary = "Получить все транзакции по Счету")
    public ResponseEntity<Collection<SberRawTransactionEntity>> getAllTransactionsByAccountId(@PathVariable Integer accountId){
        SberRawTransactionServiceImpl sberRawTransactionService = (SberRawTransactionServiceImpl) generalRawTransactionService;
        List<SberRawTransactionEntity> rawTransactionEntities = sberRawTransactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(rawTransactionEntities, HttpStatus.OK);
    }
}
