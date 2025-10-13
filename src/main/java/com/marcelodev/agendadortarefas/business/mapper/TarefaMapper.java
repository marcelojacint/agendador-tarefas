package com.marcelodev.agendadortarefas.business.mapper;

import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import com.marcelodev.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaEntity paraTarefaEntity(TarefaDTO dto);

    TarefaDTO paraTarefaDTO(TarefaEntity entity);

    List<TarefaEntity> paraListaTarefaEntity(List<TarefaDTO> dto);

    List<TarefaDTO> paraListaTarefaDTO(List<TarefaEntity> entity);
}


