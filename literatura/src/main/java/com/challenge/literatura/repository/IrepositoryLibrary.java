package com.challenge.literatura.repository;

import com.challenge.literatura.model.autores;
import com.challenge.literatura.model.lenguajes;
import com.challenge.literatura.model.libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IrepositoryLibrary extends JpaRepository<autores, Long> {
    Optional<autores> findAuthorByNameContaining(String nombre);

    @Query("SELECT l FROM Book l JOIN l.author a WHERE l.title LIKE %:btitulo%")
    Optional<libros> getBookContainsEqualsIgnoreCaseTitle(String tituloLibroUsuario);

    @Query("SELECT l From Author a join a.books ")
    List<libros> findBooksByAuthor( );

    @Query("SELECT a FROM Author a WHERE a.dateOfDecease > :date ")
    List<autores> getfechaDeFallecimiento(Integer date);

    @Query("SELECT l FROM Author a join a.books l WHERE l.language = :lenguaje")
    List<libros> findBookByLanguage(lenguajes lenguaje);
}
