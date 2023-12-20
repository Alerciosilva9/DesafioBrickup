package com.desafio.brickupdesafio.repositories;

import com.desafio.brickupdesafio.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}