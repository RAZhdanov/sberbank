package ru.sberbank.sberbank.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GeneralService<T_entity> {

    List<T_entity> findAll();

    Optional<T_entity> findById(Integer id);

    T_entity add(T_entity dto);

    void delete(T_entity dto);

    T_entity edit(T_entity dto);

    Collection<T_entity> editAll(Collection<T_entity> entityCollection);

    long count();

}
