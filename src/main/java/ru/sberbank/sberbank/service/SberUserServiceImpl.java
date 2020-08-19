package ru.sberbank.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.sberbank.entity.SberUserEntity;
import ru.sberbank.sberbank.repo.SberbankUserRepo;
import ru.sberbank.sberbank.util.GeneralServiceImpl;

@Service
public class SberUserServiceImpl extends GeneralServiceImpl<SberbankUserRepo, SberUserEntity> {

    @Autowired
    public SberUserServiceImpl(SberbankUserRepo m_iT_repo) {
        super(m_iT_repo);
    }
}
