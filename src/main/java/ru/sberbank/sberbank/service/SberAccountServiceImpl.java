package ru.sberbank.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberUserEntity;
import ru.sberbank.sberbank.repo.SberbankAccountRepo;
import ru.sberbank.sberbank.repo.SberbankUserRepo;
import ru.sberbank.sberbank.util.GeneralServiceImpl;

import java.util.List;

@Service
public class SberAccountServiceImpl extends GeneralServiceImpl<SberbankAccountRepo, SberAccountEntity> {

    @Autowired
    public SberAccountServiceImpl(SberbankAccountRepo m_iT_repo) {
        super(m_iT_repo);
    }

    public List<SberAccountEntity> getAccountsByClientId(Integer client_id){
        SberbankAccountRepo t_repo = getM_iT_repo();
        List<SberAccountEntity> accounts = t_repo.getAccountsByUserId(client_id);
        return accounts;
    }
}
