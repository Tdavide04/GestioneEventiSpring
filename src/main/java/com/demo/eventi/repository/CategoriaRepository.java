package com.demo.eventi.repository;

import com.demo.eventi.model.Categoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	Optional<Categoria> findByNome(String nome);

	boolean existsByNome(String nome);

}
