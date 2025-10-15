package com.marcelodev.agendadortarefas.business;

import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import com.marcelodev.agendadortarefas.business.mapper.TarefaMapper;
import com.marcelodev.agendadortarefas.business.enums.StatusNotificacaoEnum;
import com.marcelodev.agendadortarefas.business.mapper.TarefaUpdateMapper;
import com.marcelodev.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.marcelodev.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.marcelodev.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.marcelodev.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final TarefaUpdateMapper updateMapper;
    private final JwtUtil jwtUtil;

    public TarefaDTO salvarTarefa(String token, TarefaDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setEmailUsuario(email);
        dto.setDataAlteracao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefaEntity tarefaEntity = tarefaMapper.paraTarefaEntity(dto);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefaEntity));


    }

    public List<TarefaDTO> buscaTarefaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaMapper.paraListaTarefaDTO(
                tarefaRepository.findBydataEventoBetween(dataInicial, dataFinal
                ));
    }

    public List<TarefaDTO> buscaTarefaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefaEntity> listaTarefas = tarefaRepository.findByEmailUsuario(email);
        return tarefaMapper.paraListaTarefaDTO(listaTarefas);
    }

    public void deletarTarefa(String id) {
        tarefaRepository.deleteById(id);
    }

    public TarefaDTO alterarStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefaEntity tarefaEntity = tarefaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("tarefa não encontrada " + id));

            tarefaEntity.setStatusNotificacaoEnum(status);

            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefaEntity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status de tarefa " + e.getCause());
        }

    }

    public TarefaDTO updateTarefa(TarefaDTO dto, String id) {
        try {
            TarefaEntity tarefaEntity = tarefaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("tarefa não encontrada " + id));
            tarefaEntity.setDataAlteracao(LocalDateTime.now());
            updateMapper.updateTarefa(dto, tarefaEntity);

            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefaEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("erro na atualização da tarefa " + e.getCause());
        }
    }
}
