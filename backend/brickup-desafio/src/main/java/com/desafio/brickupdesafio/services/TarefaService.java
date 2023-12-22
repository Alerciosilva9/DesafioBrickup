package com.desafio.brickupdesafio.services;

import com.desafio.brickupdesafio.entities.Imagem;
import com.desafio.brickupdesafio.entities.Tarefa;
import com.desafio.brickupdesafio.repositories.ImagemRepository;
import com.desafio.brickupdesafio.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ImagemRepository imgRepo;

    public List<Tarefa> buscarTodos(){
        return tarefaRepository.findAll();
    }


    public Tarefa criarTarefa(Tarefa nova){
        return tarefaRepository.save(nova);
    }


    public Tarefa atualizar(Tarefa tarefa, long id){
        Optional<Tarefa> antiga = tarefaRepository.findById(id);
        if(antiga.isPresent()){
            Tarefa tf = antiga.get();
            tf.setStatus(tarefa.getStatus());
            tf.setDescricao(tarefa.getDescricao());
            return tarefaRepository.save(tf);
        }
        return null;
    }

    public Tarefa buscarTarefa(long id){
        Optional<Tarefa> task = tarefaRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        }
        return null;
    }

    public String deletar(long id){
        Optional<Tarefa> tf = tarefaRepository.findById(id);
        if(tf.isPresent()){
            tarefaRepository.deleteById(id);
            return "Sucesso";
        }
        return null;

    }



    public String uploadImagem(MultipartFile file, long id) throws IOException {
        Imagem img = new Imagem();
        Optional<Tarefa> task = tarefaRepository.findById(id);
        if(task.isPresent()){
            img.setImagem(file.getBytes());
            img.setTarefa(task.get());
            imgRepo.save(img);
            return "Imagem Cadastradas";
        }
        return null;
    }


    public byte[] buscasrImg(long id) throws IOException {
        Optional<Tarefa> tf = tarefaRepository.findById(id);
        if(tf.isPresent()){
            Optional<Imagem> img = imgRepo.findByTarefa(tf.get());
            if(img.isPresent()){
                return img.get().getImagem();
            }
        }
        return null;
    }


    public Tarefa concluirTarefa(long id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if(tarefa.isPresent()){
            tarefa.get().setStatus("CONCLUIDO");
            return tarefaRepository.save(tarefa.get());
        }
        return null;
    }
}
