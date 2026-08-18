package br.com.sanscritinho.presentation.controller;

import br.com.sanscritinho.application.dto.AlunoRequestDTO;
import br.com.sanscritinho.application.dto.AlunoResponseDTO;
import br.com.sanscritinho.application.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alunos")
@RequiredArgsConstructor
@Slf4j
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criar(@RequestBody AlunoRequestDTO dto) {
        log.info("POST /api/v1/alunos - Criando novo aluno");
        AlunoResponseDTO response = alunoService.criarAluno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> obterPorId(@PathVariable Long id) {
        log.info("GET /api/v1/alunos/{} - Obtendo aluno por ID", id);
        AlunoResponseDTO response = alunoService.obterAlunoPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoResponseDTO> obterPorMatricula(@PathVariable String matricula) {
        log.info("GET /api/v1/alunos/matricula/{} - Obtendo aluno por matrícula", matricula);
        AlunoResponseDTO response = alunoService.obterAlunoPorMatricula(matricula);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody AlunoRequestDTO dto) {
        log.info("PUT /api/v1/alunos/{} - Atualizando aluno", id);
        AlunoResponseDTO response = alunoService.atualizarAluno(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("DELETE /api/v1/alunos/{} - Deletando aluno", id);
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AlunoResponseDTO>> listarTodos(Pageable pageable) {
        log.info("GET /api/v1/alunos - Listando todos os alunos");
        Page<AlunoResponseDTO> response = alunoService.listarTodos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<Page<AlunoResponseDTO>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        log.info("GET /api/v1/alunos/buscar/nome - Buscando alunos por nome: {}", nome);
        Page<AlunoResponseDTO> response = alunoService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/nota-minima")
    public ResponseEntity<Page<AlunoResponseDTO>> buscarPorNotaMinima(
            @RequestParam Double nota,
            Pageable pageable) {
        log.info("GET /api/v1/alunos/buscar/nota-minima - Buscando alunos com nota >= {}", nota);
        Page<AlunoResponseDTO> response = alunoService.listarAlunosPorNotaMinima(nota, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/nota-maxima")
    public ResponseEntity<Page<AlunoResponseDTO>> buscarPorNotaMaxima(
            @RequestParam Double nota,
            Pageable pageable) {
        log.info("GET /api/v1/alunos/buscar/nota-maxima - Buscando alunos com nota <= {}", nota);
        Page<AlunoResponseDTO> response = alunoService.listarAlunosPorNotaMaxima(nota, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/intervalo-nota")
    public ResponseEntity<Page<AlunoResponseDTO>> buscarPorIntervaloNota(
            @RequestParam Double notaMinima,
            @RequestParam Double notaMaxima,
            Pageable pageable) {
        log.info("GET /api/v1/alunos/buscar/intervalo-nota - Buscando alunos entre {} e {}", 
                notaMinima, notaMaxima);
        Page<AlunoResponseDTO> response = alunoService.listarAlunosPorIntervaloNota(
                notaMinima,
                notaMaxima,
                pageable
        );
        return ResponseEntity.ok(response);
    }
}
