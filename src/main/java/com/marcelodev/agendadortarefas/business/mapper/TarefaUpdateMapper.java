package com.marcelodev.agendadortarefas.business.mapper;

import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import com.marcelodev.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateMapper {

    void updateTarefa(TarefaDTO dto, @MappingTarget TarefaEntity entity);
}
