package com.marcelodev.agendadortarefas.controller;

import com.marcelodev.agendadortarefas.business.TarefaService;
import com.marcelodev.agendadortarefas.business.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDTO>> buscaListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {

        List<TarefaDTO> tarefaDTOS = tarefaService.buscaTarefaPorPeriodo(dataInicial, dataFinal);

        return ResponseEntity.ok(tarefaDTOS);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscaTarefaPorEmail(@RequestHeader("Authorization") String token) {
        List<TarefaDTO> tarefaDTOS = tarefaService.buscaTarefaPorEmail(token);

        return ResponseEntity.ok(tarefaDTOS);
    }

    private URI geraUri(TarefaDTO tarefaSalva) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("id")
                .buildAndExpand(tarefaSalva.getId())
                .toUri();

    }
}
