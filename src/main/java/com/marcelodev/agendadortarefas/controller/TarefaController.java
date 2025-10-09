package com.marcelodev.agendadortarefas.controller;

import com.marcelodev.agendadortarefas.business.TarefaService;
import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> salvaTarefa(@RequestHeader("Authorization") String token, @RequestBody TarefaDTO dto) {
        TarefaDTO tarefaSalva = tarefaService.salvarTarefa(token, dto);
        URI uri = geraUri(tarefaSalva);
        return ResponseEntity.created(uri).body(tarefaSalva);

    }

    private URI geraUri(TarefaDTO tarefaSalva) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("id")
                .buildAndExpand(tarefaSalva.getId())
                .toUri();

    }
}
