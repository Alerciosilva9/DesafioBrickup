package com.desafio.brickupdesafio.controllers;

import com.desafio.brickupdesafio.dtos.TarefaDTO;
import com.desafio.brickupdesafio.entities.Imagem;
import com.desafio.brickupdesafio.entities.Tarefa;
import com.desafio.brickupdesafio.repositories.ImagemRepository;
import com.desafio.brickupdesafio.repositories.TarefaRepository;
import com.desafio.brickupdesafio.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "http://localhost:3000")
public class TarefaController {

    @Autowired
    TarefaService service;
    @GetMapping()
    public List<TarefaDTO> findAll(){
        return service.buscarTodos();
    }

    @PostMapping()
    public ResponseEntity<TarefaDTO> create(@Valid @RequestBody TarefaDTO nova){
        return ResponseEntity.ok(service.criarTarefa(nova));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@RequestBody Tarefa tarefa, @PathVariable long id){
        Tarefa nova = service.atualizar(tarefa,id);
        if(tarefa!=null){
            return ResponseEntity.ok().body(nova);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefaporId(@PathVariable long id){
        TarefaDTO task = service.buscarTarefa(id);
        if(task!=null){
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<String> uploadImagem(@RequestBody MultipartFile file, @PathVariable long id) throws IOException {
        String upload = service.uploadImagem(file,id);
        if(upload!=null){
            return ResponseEntity.ok("Imagem Cadastradas");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{id}/imagem", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] buscasrImg(@PathVariable long id) throws IOException {
        return service.buscasrImg(id);
    }

    @GetMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluirTarefa(@PathVariable long id){
        Tarefa tarefa = service.concluirTarefa(id);
        if(tarefa!=null){
            return ResponseEntity.ok().body(tarefa);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTarefa(@PathVariable long id){
        String ss = service.deletar(id);
        if(ss!=null){
            return ResponseEntity.ok().body(ss);
        }
        return ResponseEntity.notFound().build();
    }



}
