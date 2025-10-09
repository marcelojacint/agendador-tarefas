package com.marcelodev.agendadortarefas.business;

import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import com.marcelodev.agendadortarefas.business.mapper.TarefaMapper;
import com.marcelodev.agendadortarefas.enums.StatusNotificacaoEnum;
import com.marcelodev.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.marcelodev.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.marcelodev.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    public TarefaDTO salvarTarefa(String token, TarefaDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setEmailUsuario(email);
        dto.setDataAlteracao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefaEntity tarefaEntity = tarefaMapper.paraTarefaEntity(dto);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefaEntity));


    }
}
