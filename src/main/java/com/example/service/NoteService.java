package com.example.service;
import com.example.model.Note;
import com.example.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository repo;
    public NoteService(NoteRepository repo){ this.repo = repo; }

    public List<Note> listAll(){ return repo.findAll(); }
    public Note get(Long id){ return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Note not found")); }
    public Note create(Note n){ n.setId(null); return repo.save(n); }
    public Note update(Long id, Note n){
        Note ex = repo.findById(id).orElseThrow();
        ex.setTitle(n.getTitle());
        ex.setContent(n.getContent());
        return repo.save(ex);
    }
    public void delete(Long id){ repo.deleteById(id); }

    public Optional<Note> getNoteById(Long id) {
        return repo.findById(id);
    }

}
