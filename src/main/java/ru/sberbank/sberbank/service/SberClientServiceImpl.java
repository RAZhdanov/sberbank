package ru.sberbank.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.repo.SberbankAccountRepo;
import ru.sberbank.sberbank.repo.SberbankClientRepo;
import ru.sberbank.sberbank.util.GeneralServiceImpl;

@Service
public class SberClientServiceImpl extends GeneralServiceImpl<SberbankClientRepo, SberClientEntity>  {

    @Autowired
    public SberClientServiceImpl(SberbankClientRepo m_iT_repo) {
        super(m_iT_repo);
    }


}
