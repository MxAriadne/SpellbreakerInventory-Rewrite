package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
