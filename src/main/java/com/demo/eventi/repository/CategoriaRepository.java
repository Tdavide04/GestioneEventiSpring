package com.demo.eventi.repository;

import com.demo.eventi.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}

