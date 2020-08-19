package ru.sberbank.sberbank.repo;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.util.GeneralRepository;

import java.util.List;

@Repository
public interface SberbankAccountRepo extends GeneralRepository<SberAccountEntity> {
    @Override
    @Query("SELECT a FROM SberAccountEntity a order by a.id")
    List<SberAccountEntity> getAll();

    @Query("SELECT acc FROM SberAccountEntity acc WHERE acc.id IN (SELECT a.id FROM SberClientEntity a WHERE a.userId.id = :id)")
    List<SberAccountEntity> getAccountsByUserId(@Param("id") Integer id);


}
