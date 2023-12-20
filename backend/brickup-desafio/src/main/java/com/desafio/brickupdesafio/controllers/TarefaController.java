package com.desafio.brickupdesafio.controllers;

import com.desafio.brickupdesafio.entities.Tarefa;
import com.desafio.brickupdesafio.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repo;

    @GetMapping()
    public List<Tarefa> buscarTodos(){
        return repo.findAll();
    }

    @PostMapping()
    public Tarefa criarTarefa(@RequestBody Tarefa nova){
        return repo.save(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@RequestBody Tarefa tarefa, @PathVariable long id){
        Optional<Tarefa> antiga = repo.findById(id);
        if(antiga.isPresent()){
            Tarefa tf = antiga.get();
            tf.setStatus(tarefa.getStatus());
            tf.setDescricao(tarefa.getDescricao());
            return ResponseEntity.ok(repo.save(tf));
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefa(@RequestBody Tarefa tarefa, @PathVariable long id){
        Optional<Tarefa> task = repo.findById(id);
        if(task.isPresent()){
            return ResponseEntity.ok(task.get());
        }
        return ResponseEntity.notFound().build();
    }




}
