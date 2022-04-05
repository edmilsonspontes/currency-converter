package com.elektron.currency.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elektron.currency.entity.TransactionEntity;
import com.elektron.currency.entity.UserEntity;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

	  List findByUser(UserEntity user);
}