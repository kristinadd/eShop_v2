package com.kristina.ecom.dao;

import com.mongodb.MongoException;

import java.util.List;

public interface MongoDAO<K, V> {
   V create(V v) throws DAOException;
   List<V> readAll() throws DAOException;
   V read(K k) throws DAOException;
   int update(V v) throws DAOException;
   int delete(K k) throws DAOException;
}
