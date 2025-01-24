package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.PartsSku;
import com.freyja.spellbreaker.entities.PoItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SKURepository extends CrudRepository<PartsSku, Integer> {
}

