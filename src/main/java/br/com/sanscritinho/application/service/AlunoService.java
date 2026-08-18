package br.com.sanscritinho.application.service;

import br.com.sanscritinho.application.dto.AlunoRequestDTO;
import br.com.sanscritinho.application.dto.AlunoResponseDTO;
import br.com.sanscritinho.domain.model.Aluno;
import br.com.sanscritinho.infrastructure.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Transactional
    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {
        log.info("Criando novo aluno: {}", dto.getMatricula());
        
        if (alunoRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            throw new IllegalArgumentException("Matrícula já existe: " + dto.getMatricula());
        }

        Aluno aluno = Aluno.builder()
                .nome(dto.getNome())
                .matricula(dto.getMatricula())
                .nota(dto.getNota())
                .observacoes(dto.getObservacoes())
                .build();

        Aluno salvo = alunoRepository.save(aluno);
        log.info("Aluno criado com sucesso: ID {}", salvo.getId());
        
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public AlunoResponseDTO obterAlunoPorId(Long id) {
        log.info("Buscando aluno com ID: {}", id);
        
        return alunoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public AlunoResponseDTO obterAlunoPorMatricula(String matricula) {
        log.info("Buscando aluno com matrícula: {}", matricula);
        
        return alunoRepository.findByMatricula(matricula)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + matricula));
    }

    @Transactional
    public AlunoResponseDTO atualizarAluno(Long id, AlunoRequestDTO dto) {
        log.info("Atualizando aluno: {}", id);
        
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));

        aluno.setNome(dto.getNome());
        aluno.setNota(dto.getNota());
        aluno.setObservacoes(dto.getObservacoes());

        Aluno atualizado = alunoRepository.save(aluno);
        log.info("Aluno atualizado com sucesso: {}", id);
        
        return toResponseDTO(atualizado);
    }

    @Transactional
    public void deletarAluno(Long id) {
        log.info("Deletando aluno: {}", id);
        
        if (!alunoRepository.existsById(id)) {
            throw new IllegalArgumentException("Aluno não encontrado: " + id);
        }

        alunoRepository.deleteById(id);
        log.info("Aluno deletado com sucesso: {}", id);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponseDTO> listarAlunosPorNotaMinima(Double notaMinima, Pageable pageable) {
        log.info("Buscando alunos com nota mínima: {}", notaMinima);
        
        return alunoRepository.findByNotaGreaterThanEqual(notaMinima, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponseDTO> listarAlunosPorNotaMaxima(Double notaMaxima, Pageable pageable) {
        log.info("Buscando alunos com nota máxima: {}", notaMaxima);
        
        return alunoRepository.findByNotaLessThanEqual(notaMaxima, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponseDTO> listarAlunosPorIntervaloNota(
            Double notaMinima,
            Double notaMaxima,
            Pageable pageable) {
        log.info("Buscando alunos com notas entre {} e {}", notaMinima, notaMaxima);
        
        return alunoRepository.findByNotaBetween(notaMinima, notaMaxima, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        log.info("Buscando alunos por nome: {}", nome);
        
        return alunoRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponseDTO> listarTodos(Pageable pageable) {
        log.info("Listando todos os alunos");
        
        return alunoRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    private AlunoResponseDTO toResponseDTO(Aluno aluno) {
        return AlunoResponseDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .matricula(aluno.getMatricula())
                .nota(aluno.getNota())
                .dataCadastro(aluno.getDataCadastro())
                .dataAtualizacao(aluno.getDataAtualizacao())
                .observacoes(aluno.getObservacoes())
                .build();
    }
}
