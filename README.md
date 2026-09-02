# Agendador de Tarefas

Microserviço de agendamento de tarefas com notificação por e-mail. Faz parte de um conjunto de três serviços que conversam entre si:

- `usuario` cuida do cadastro, do login e da emissão do token
- `agendador-tarefas` (este) guarda as tarefas e o status de notificação
- `notificacao` dispara o e-mail

Toda requisição chega com o token no header `Authorization`. O serviço valida o token e, quando precisa dos dados do dono da tarefa, chama o serviço de usuário via OpenFeign (`UsuarioClient`) em vez de acessar o banco do outro serviço.

## Stack

Java 17, Spring Boot 3.5.6, Spring Security com JWT (jjwt), Spring Cloud OpenFeign, MongoDB, MapStruct e Lombok.

## Endpoints

| Método | Rota | O que faz |
| --- | --- | --- |
| POST | `/tarefa` | cria a tarefa a partir do usuário do token |
| GET | `/tarefa` | lista as tarefas do usuário do token |
| GET | `/tarefa/eventos?dataInicial=&dataFinal=` | lista as tarefas de um período |
| PUT | `/tarefa?id=` | atualiza a tarefa |
| PATCH | `/tarefa?status=&id=` | muda o status da notificação |
| DELETE | `/tarefa?id=` | remove a tarefa |

## Rodando

Sobe na porta `8082` e espera um MongoDB local e o serviço de usuário na `8080`:

```
spring.data.mongodb.uri=mongodb://admin:admin@localhost:27017/db_agendador?authSource=admin
usuario.url=localhost:8080
```

```bash
./gradlew bootRun
```

Exemplo de criação de tarefa:

```bash
curl -X POST http://localhost:8082/tarefa \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeTarefa": "Consulta",
    "descricao": "Levar os exames",
    "dataEvento": "15-10-2026 14:30:00"
  }'
```

## Próximos passos

- Subir os três serviços juntos com docker-compose
- Job agendado para varrer as tarefas do período e acionar o serviço de notificação
# Agendador de Tarefas

## Microserviço para projeto de agendamento de tarefas
