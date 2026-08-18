package br.com.sanscritinho.application.service;

import br.com.sanscritinho.application.dto.AlunoRequestDTO;
import br.com.sanscritinho.application.dto.AlunoResponseDTO;
import br.com.sanscritinho.domain.model.Aluno;
import br.com.sanscritinho.infrastructure.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Serviço de Alunos")
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private AlunoRequestDTO alunoRequestDTO;
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        // Preparar dados de teste
        alunoRequestDTO = AlunoRequestDTO.builder()
                .nome("João Silva")
                .matricula("2024001")
                .nota(8.5)
                .observacoes("Desempenho excelente")
                .build();

        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .matricula("2024001")
                .nota(8.5)
                .dataCadastro(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .observacoes("Desempenho excelente")
                .build();
    }

    @Test
    @DisplayName("Deve criar um aluno com sucesso")
    void testCriarAluno_Sucesso() {
        // Arrange
        when(alunoRepository.findByMatricula("2024001")).thenReturn(Optional.empty());
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoResponseDTO resultado = alunoService.criarAluno(alunoRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("2024001", resultado.getMatricula());
        assertEquals(8.5, resultado.getNota());
        verify(alunoRepository, times(1)).findByMatricula("2024001");
        verify(alunoRepository, times(1)).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar aluno com matrícula duplicada")
    void testCriarAluno_MatriculaDuplicada() {
        // Arrange
        when(alunoRepository.findByMatricula("2024001")).thenReturn(Optional.of(aluno));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            alunoService.criarAluno(alunoRequestDTO);
        });

        verify(alunoRepository, times(1)).findByMatricula("2024001");
        verify(alunoRepository, never()).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve obter aluno por ID com sucesso")
    void testObterAlunoPorId_Sucesso() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // Act
        AlunoResponseDTO resultado = alunoService.obterAlunoPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao obter aluno com ID inexistente")
    void testObterAlunoPorId_NaoEncontrado() {
        // Arrange
        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            alunoService.obterAlunoPorId(999L);
        });

        verify(alunoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve deletar aluno com sucesso")
    void testDeletarAluno_Sucesso() {
        // Arrange
        when(alunoRepository.existsById(1L)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> alunoService.deletarAluno(1L));

        // Assert
        verify(alunoRepository, times(1)).existsById(1L);
        verify(alunoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve listar alunos por intervalo de nota com sucesso")
    void testListarAlunosPorIntervaloNota_Sucesso() {
        // Arrange
        List<Aluno> alunos = List.of(aluno);
        Page<Aluno> page = new PageImpl<>(alunos);
        when(alunoRepository.findByNotaBetween(
                eq(7.0),
                eq(9.0),
                any(PageRequest.class)
        )).thenReturn(page);

        // Act
        Page<AlunoResponseDTO> resultado = alunoService.listarAlunosPorIntervaloNota(
                7.0,
                9.0,
                PageRequest.of(0, 10)
        );

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(alunoRepository, times(1)).findByNotaBetween(
                eq(7.0),
                eq(9.0),
                any(PageRequest.class)
        );
    }
}
