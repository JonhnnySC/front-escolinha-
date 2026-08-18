package br.com.sanscritinho.infrastructure.repository;

import br.com.sanscritinho.domain.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByMatricula(String matricula);

    @Query("SELECT a FROM Aluno a WHERE a.nota >= :notaMinima ORDER BY a.nota DESC")
    Page<Aluno> findByNotaGreaterThanEqual(
            @Param("notaMinima") Double notaMinima,
            Pageable pageable
    );

    @Query("SELECT a FROM Aluno a WHERE a.nota <= :notaMaxima ORDER BY a.nota ASC")
    Page<Aluno> findByNotaLessThanEqual(
            @Param("notaMaxima") Double notaMaxima,
            Pageable pageable
    );

    @Query("SELECT a FROM Aluno a WHERE a.nota BETWEEN :notaMinima AND :notaMaxima ORDER BY a.nota DESC")
    Page<Aluno> findByNotaBetween(
            @Param("notaMinima") Double notaMinima,
            @Param("notaMaxima") Double notaMaxima,
            Pageable pageable
    );

    @Query("SELECT a FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Aluno> findByNomeContainingIgnoreCase(
            @Param("nome") String nome,
            Pageable pageable
    );
}
