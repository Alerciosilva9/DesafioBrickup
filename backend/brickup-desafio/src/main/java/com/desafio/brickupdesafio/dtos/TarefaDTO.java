package com.desafio.brickupdesafio.dtos;

import com.desafio.brickupdesafio.entities.Tarefa;
import jakarta.validation.constraints.NotBlank;

public class TarefaDTO {
    private long id;

    @NotBlank(message = "STATUS É OBRIGATORIO")
    private String status;
    @NotBlank(message = "Descrição da tarefa é obrigatorio")
    private String descricao;

    public TarefaDTO(){}

    public  TarefaDTO(Tarefa entity){
        this.descricao = entity.getDescricao();
        this.id = entity.getId();
        this.status = entity.getStatus();
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
