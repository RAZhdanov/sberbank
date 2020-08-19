package ru.sberbank.sberbank.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.entity.SberRawTransactionEntity;
import ru.sberbank.sberbank.util.GeneralRepository;

import java.util.List;

@Repository
public interface SberbankRawTransactionRepo extends GeneralRepository<SberRawTransactionEntity> {
    @Override
    @Query("SELECT a FROM SberRawTransactionEntity a order by a.id")
    List<SberRawTransactionEntity> getAll();

    @Query("SELECT a FROM SberRawTransactionEntity a WHERE a.accountId.id = :accountId")
    List<SberRawTransactionEntity> getTransactionsByAccountId(@Param("accountId") Integer accountId);
}
