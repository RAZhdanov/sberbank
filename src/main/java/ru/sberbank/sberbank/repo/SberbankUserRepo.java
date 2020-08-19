package ru.sberbank.sberbank.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.entity.SberUserEntity;
import ru.sberbank.sberbank.util.GeneralRepository;

import java.util.List;

@Repository
public interface SberbankUserRepo extends GeneralRepository<SberUserEntity> {
    @Override
    @Query("SELECT a FROM SberUserEntity a order by a.id")
    List<SberUserEntity> getAll();
}
