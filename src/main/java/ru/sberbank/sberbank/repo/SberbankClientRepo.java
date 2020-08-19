package ru.sberbank.sberbank.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sberbank.sberbank.entity.SberAccountEntity;
import ru.sberbank.sberbank.entity.SberClientEntity;
import ru.sberbank.sberbank.util.GeneralRepository;

import java.util.List;

@Repository
public interface SberbankClientRepo extends GeneralRepository<SberClientEntity> {
    @Override
    @Query("SELECT a FROM SberClientEntity a order by a.id")
    List<SberClientEntity> getAll();
}
