package com.demo.eventi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.eventi.model.Categoria;
import com.demo.eventi.repository.CategoriaRepository;
import com.demo.eventi.service.ICategoriaService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService implements ICategoriaService {

	private final CategoriaRepository categoriaRepository;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@Override
	public Categoria creaCategoria(Categoria categoria) throws Exception {
		if (categoriaRepository.existsByNome(categoria.getNome())) {
			throw new Exception("Nome già in uso");
		}
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria aggiornaCategoria(Categoria categoria) throws Exception {
		Optional<Categoria> esistente = categoriaRepository.findByNome(categoria.getNome());

		if (esistente.isPresent() && !esistente.get().getIdCategoria().equals(categoria.getIdCategoria())) {
			throw new Exception("Nome già in uso da un'altra categoria");
		}

		return categoriaRepository.save(categoria);
	}

	@Override
	public void eliminaCategoria(Long id) throws Exception {
		if (!categoriaRepository.existsById(id)) {
			throw new Exception("Categoria non trovata per ID: " + id);
		}
		categoriaRepository.deleteById(id);
	}

	@Override
	public Optional<Categoria> trovaPerId(Long id) {
		return categoriaRepository.findById(id);
	}

	@Override
	public Optional<Categoria> trovaPerNome(String nome) {
		return categoriaRepository.findByNome(nome);
	}

}
