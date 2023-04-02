package com.client.ws.rasmooplus.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonMappingException;

public interface JpaRepositoryModel<T> {

    List<T> findAll() throws JsonMappingException;
    T findById(Long id) throws JsonMappingException;
    T create(T modelDto) throws JsonMappingException;
    T update(Long id, T modelDto);
    Void deleteById(Long id);
}
