package ru.sberbank.sberbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberRawTransactionEntity;
import ru.sberbank.sberbank.service.SberRawTransactionServiceImpl;
import ru.sberbank.sberbank.util.GeneralRestController;
import ru.sberbank.sberbank.util.GeneralService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountRestControllerV1 extends GeneralRestController<AccountRestControllerV1, GeneralService<SberAccountEntity>, SberAccountEntity> {


    private final GeneralService<SberRawTransactionEntity> generalRawTransactionService;

    @Autowired
    public AccountRestControllerV1(GeneralService<SberAccountEntity> m_iTservice, GeneralService<SberRawTransactionEntity> generalRawTransactionService) {
        super(m_iTservice,AccountRestControllerV1.class);
        this.generalRawTransactionService = generalRawTransactionService;
    }

    @Override
    protected Links createLinks(Integer id, HttpServletRequest request, SberAccountEntity optEntity, String strSelfHref) throws Exception {
        Links links = super.createLinks(id, request, optEntity, strSelfHref);
        links.and(new Link(linkTo(methodOn(getEntityClass()).sendMoney(null, null, null, null)).toUri().getPath(),"sendMoney"));
        return links;
    }

    @PostMapping("/sendMoney/{sourceAccountId:[+]?[\\d]+}/{targetAccountId:[+]?[\\d]+}/{amount:[+]?[0-9]*\\.?[0-9]+}")
    @Operation(summary = "Перевести деньги со Счета A на Счет B")
    @Transactional(rollbackFor=Exception.class)
    public ResponseEntity<CollectionModel<SberAccountEntity>> sendMoney
    (
            HttpServletRequest request,
            @PathVariable("sourceAccountId") Integer sourceAccountId,
            @PathVariable("targetAccountId") Integer targetAccountId,
            @PathVariable("amount") Double amount
    ) throws Exception {
        //INITIALIZATION BLOCK
        GeneralService<SberAccountEntity> tservice = getM_iTservice();
        Optional<SberAccountEntity> source = tservice.findById(sourceAccountId);
        Optional<SberAccountEntity> target = tservice.findById(targetAccountId);

        if(!source.isPresent() || !target.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if(amount == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SberAccountEntity sberAccountEntitySource = source.get();
        SberAccountEntity sberAccountEntityTarget = target.get();

        Links sourceDefaultLinks = createLinks(sourceAccountId, request, sberAccountEntitySource, null);
        Links targetDefaultLinks = createLinks(targetAccountId, request, sberAccountEntityTarget, null);

        sberAccountEntitySource.add(sourceDefaultLinks);
        sberAccountEntityTarget.add(targetDefaultLinks);

        //Collections for batch sql-operations
        Collection<SberAccountEntity> sberAccountEntities = new ArrayList<>();
        Collection<SberRawTransactionEntity> sberRawTransactionEntities = new ArrayList<>();

        //PROCESSING BLOCK
        if(sberAccountEntitySource.getCash() < amount){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //We have to subtract given amount from source account and add it to target account... Let's do it.
        sberAccountEntitySource.setCash(sberAccountEntitySource.getCash() - amount);
        sberAccountEntityTarget.setCash(sberAccountEntityTarget.getCash() + amount);

        //Table #1: Let's create a batch request for 'account' and execute it
        sberAccountEntities.add(sberAccountEntitySource);
        sberAccountEntities.add(sberAccountEntityTarget);
        Collection<SberAccountEntity> newSberAccountEntities = tservice.editAll(sberAccountEntities);

        //Table #2: Let's create a batch request for 'rawTransaction' and execute it
        sberRawTransactionEntities.add(new SberRawTransactionEntity(amount * -1, sberAccountEntitySource.getCash(), new Date(), sberAccountEntitySource));
        sberRawTransactionEntities.add(new SberRawTransactionEntity(amount, sberAccountEntityTarget.getCash(), new Date(), sberAccountEntityTarget));
        generalRawTransactionService.editAll(sberRawTransactionEntities);

        return new ResponseEntity<>(CollectionModel.of(newSberAccountEntities), HttpStatus.OK);
    }

    @GetMapping("/{accountId:[+]?[\\d]+}/transactions")
    @Operation(summary = "Получить все транзакции по Счету")
    public ResponseEntity<Collection<SberRawTransactionEntity>> getAllTransactionsByAccountId(@PathVariable Integer accountId){
        SberRawTransactionServiceImpl sberRawTransactionService = (SberRawTransactionServiceImpl) generalRawTransactionService;
        List<SberRawTransactionEntity> rawTransactionEntities = sberRawTransactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(rawTransactionEntities, HttpStatus.OK);
    }
}
