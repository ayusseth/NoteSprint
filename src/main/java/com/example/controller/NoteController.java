package com.example.controller;
import com.example.model.Note;
import com.example.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService service;
    public NoteController(NoteService service){ this.service = service; }
    @Operation(summary = "Get all notes", description = "Returns a list of all notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of notes retrieved successfully")
    })

    @GetMapping
    public List<Note> all(){ return service.listAll(); }

    @Operation(summary = "Get note by ID", description = "Fetch a single note by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public Note get(@PathVariable Long id){ return service.get(id); }
    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = service.getNoteById(id);
        return note.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Create a new note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully")
    })
    @PostMapping
    public Note create(@RequestBody Note n){ return service.create(n); }

    @Operation(summary = "Update a note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @RequestBody Note n){ return service.update(id, n); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
