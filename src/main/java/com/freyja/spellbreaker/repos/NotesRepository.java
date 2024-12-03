package com.freyja.spellbreaker.repos;

import com.freyja.spellbreaker.entities.Device;
import com.freyja.spellbreaker.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NotesRepository extends CrudRepository<Note, Integer> {
    Collection<Note> findAllByDevice(Device device);

    Device getDeviceByNote(Note note);
}
