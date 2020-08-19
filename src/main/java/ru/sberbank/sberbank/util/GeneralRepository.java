package ru.sberbank.sberbank.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GeneralRepository<T_entity> extends JpaRepository<T_entity, Integer> {
    List<T_entity> getAll();

}
