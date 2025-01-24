package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Integer> {
}
