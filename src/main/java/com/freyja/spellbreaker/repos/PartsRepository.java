package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.Device;
import com.freyja.spellbreaker.entities.Note;
import com.freyja.spellbreaker.entities.PartsIndividual;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PartsRepository extends CrudRepository<PartsIndividual, Integer> {
    Collection<PartsIndividual> findAllByDevice(Device device);
}
