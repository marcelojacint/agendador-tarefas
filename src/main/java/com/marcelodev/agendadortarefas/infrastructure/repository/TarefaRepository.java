package com.marcelodev.agendadortarefas.infrastructure.repository;

import com.marcelodev.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends MongoRepository<TarefaEntity, String> {

    List<TarefaEntity> findBydataEventoBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<TarefaEntity> findByEmailUsuario(String email);
}
