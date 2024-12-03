package com.freyja.spellbreaker.controllers;

import com.freyja.spellbreaker.entities.Device;
import com.freyja.spellbreaker.entities.Note;
import com.freyja.spellbreaker.entities.PartsIndividual;
import com.freyja.spellbreaker.entities.User;
import com.freyja.spellbreaker.repos.DeviceRepository;
import com.freyja.spellbreaker.repos.NotesRepository;
import com.freyja.spellbreaker.repos.PartsRepository;
import com.freyja.spellbreaker.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.Instant;

@Controller
public class DeviceController {

    @Autowired
    private PartsRepository partRepo;

    @Autowired
    private DeviceRepository deviceRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NotesRepository notesRepo;

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @RequestParam BigDecimal price, @RequestParam String deviceId) {
        PartsIndividual part = partRepo.findById(id).get();
        part.setPrice(price);

        return "redirect:/devices/" + id;
    }

    @PostMapping("/add-part/{id}")
    public String addPart(@PathVariable Integer id, @RequestParam String partInput) {
        try {
            PartsIndividual part = partRepo.findById(Integer.valueOf(partInput)).get();
            Device device = deviceRepo.findById(id).get();

            if (part.getDevice() != null) {
                return "redirect:/devices/" + id + "?part-use-error=true";
            } else {
                part.setDevice(device);
                partRepo.save(part);
                return "redirect:/devices/" + id;
            }

        } catch (Exception e) {
            return "redirect:/devices/" + id + "?part-mismatch-error=true";
        }


    }

    @PostMapping("/add-note/{id}")
    public String addNote(@PathVariable Integer id, String noteText) {
        User u = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Note note = new Note();
        note.setNote(noteText);
        note.setDevice(deviceRepo.findById(id).get());
        note.setTimestamp(Instant.now());
        note.setCreatedBy(u);
        notesRepo.save(note);

        return "redirect:/devices/" + id;
    }

    @PostMapping("/delete-note/{id}")
    public String deleteNote(@PathVariable Integer id, Integer deviceId) {
        notesRepo.deleteById(id);
        return "redirect:/devices/" + deviceId;
    }
}
