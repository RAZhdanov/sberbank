package ru.sberbank.sberbank.service;

import org.springframework.stereotype.Service;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.entity.SberRawTransactionEntity;
import ru.sberbank.sberbank.repo.SberbankClientRepo;
import ru.sberbank.sberbank.repo.SberbankRawTransactionRepo;
import ru.sberbank.sberbank.util.GeneralServiceImpl;

import java.util.List;

@Service
public class SberRawTransactionServiceImpl extends GeneralServiceImpl<SberbankRawTransactionRepo, SberRawTransactionEntity> {

    public SberRawTransactionServiceImpl(SberbankRawTransactionRepo m_iT_repo) {
        super(m_iT_repo);
    }

    public List<SberRawTransactionEntity> getTransactionsByAccountId(Integer accountId){
        SberbankRawTransactionRepo t_repo = getM_iT_repo();
        return t_repo.getTransactionsByAccountId(accountId);
    }
}
