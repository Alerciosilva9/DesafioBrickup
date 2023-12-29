package com.desafio.brickupdesafio.entities;

import com.desafio.brickupdesafio.dtos.TarefaDTO;
import com.desafio.brickupdesafio.enums.STATUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;
    private String descricao;

    public Tarefa(){}


    public Tarefa(TarefaDTO dto){
        this.id = dto.getId();
        this.status = dto.getStatus();
        this.descricao = dto.getDescricao();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }




}