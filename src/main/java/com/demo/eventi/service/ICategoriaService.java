package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Categoria;

public interface ICategoriaService {
	Categoria creaCategoria(Categoria categoria) throws Exception;
	
	Optional<Categoria> trovaPerId(Long id);
	
	Optional<Categoria> trovaPerNome(String nome);
}
