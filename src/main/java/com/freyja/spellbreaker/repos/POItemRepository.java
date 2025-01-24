package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.PoItem;
import com.freyja.spellbreaker.entities.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface POItemRepository extends CrudRepository<PoItem, Integer> {
    List<PoItem> findAllByPoId(Integer poId);
}
