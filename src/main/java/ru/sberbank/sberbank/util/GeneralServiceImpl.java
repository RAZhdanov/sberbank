package ru.sberbank.sberbank.util;

import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
public class GeneralServiceImpl
        <
                T_repo extends GeneralRepository<T_entity>,
                T_entity extends GeneralEntity<T_entity>
                > implements GeneralService<T_entity> {


    private final T_repo m_iT_repo;

    public GeneralServiceImpl(T_repo m_iT_repo) {
        this.m_iT_repo = m_iT_repo;
    }

    @Override
    public List<T_entity> findAll() {
        return m_iT_repo.getAll();
    }

    @Override
    public Optional<T_entity> findById(Integer id) {
        return m_iT_repo.findById(id);
    }

    @Override
    public T_entity add(T_entity dto) {
        return null;
    }

    @Override
    public void delete(T_entity dto) {

    }

    @Override
    public T_entity edit(T_entity dto) {
        T_entity t_entity = m_iT_repo.save(dto);
        return t_entity;
    }

    @Override
    public Collection<T_entity> editAll(Collection<T_entity> entityCollection) {
        return m_iT_repo.saveAll(entityCollection);
    }

    @Override
    public long count() {
        return 0;
    }
}
