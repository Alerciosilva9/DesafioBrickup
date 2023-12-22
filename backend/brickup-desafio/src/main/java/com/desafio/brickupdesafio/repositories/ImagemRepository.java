package com.desafio.brickupdesafio.repositories;

import com.desafio.brickupdesafio.entities.Imagem;
import com.desafio.brickupdesafio.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    public Optional<Imagem> findByTarefa(Tarefa tarefa);
}