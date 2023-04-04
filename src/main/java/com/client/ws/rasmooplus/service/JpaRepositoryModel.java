package com.client.ws.rasmooplus.service;

import java.util.List;

public interface JpaRepositoryModel<T> {

    List<T> findAll();
    T findById(Long id);
    T create(T modelDto);
    T update(Long id, T modelDto);
    Void deleteById(Long id);
}
