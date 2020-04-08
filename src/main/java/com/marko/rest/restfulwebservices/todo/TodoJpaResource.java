package com.marko.rest.restfulwebservices.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoJpaResource {

    @Autowired
    private TodoHardcodedService todoService;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @GetMapping("/jpa/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable("username") String user){
        return todoJpaRepository.findByUsername(user);
    }

    @DeleteMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("username")String username, @PathVariable("id") long id){
        //Todo todo = todoService.deleteById(id);
        todoJpaRepository.deleteById(id);
        //if(todo!=null){
            return ResponseEntity.noContent().build();
        //}
        //return ResponseEntity.notFound().build();
    }

    @GetMapping("/jpa/users/{username}/todos/{id}")
    public Optional<Todo> getTodo(@PathVariable("username") String user, @PathVariable("id") long id){
        return todoJpaRepository.findById(id);
        //return todoService.findById(id);
    }

    @PutMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("username") String user,@PathVariable("id") long id,@RequestBody Todo todo){
        //Todo todoUpdated = todoService.save(todo);
        Todo todoUpdated = todoJpaRepository.save(todo);
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @PostMapping("/jpa/users/{username}/todos")
    public ResponseEntity<Void> updateTodo(@PathVariable("username") String username,@RequestBody Todo todo){
        //Todo todoCreated = todoService.save(todo);
        todo.setUsername(username);
        Todo todoCreated = todoJpaRepository.save(todo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoCreated.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

}
