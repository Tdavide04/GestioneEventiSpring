package com.demo.eventi.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.eventi.model.Utente;
import com.demo.eventi.repository.UtenteRepository;
import com.demo.eventi.service.IUtenteService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UtenteService implements IUtenteService {

	private final UtenteRepository utenteRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}

	@Override
	public Utente registraUtente(Utente utente) throws Exception {
		if (utenteRepository.existsByUsername(utente.getUsername())) {
			throw new Exception("Username già in uso");
		}
		if (utenteRepository.existsByEmail(utente.getEmail())) {
			throw new Exception("Email già in uso");
		}
		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		return utenteRepository.save(utente);
	}

	@Override
	public Optional<Utente> trovaPerUsername(String username) {
		return utenteRepository.findByUsername(username);
	}

	@Override
	public boolean esisteUsername(String username) {
		return utenteRepository.existsByUsername(username);
	}

	@Override
	public boolean esisteEmail(String email) {
		return utenteRepository.existsByEmail(email);
	}

	@Override
	public boolean verificaPassword(String username, String rawPassword) throws Exception {
		Utente utente = utenteRepository.findByUsername(username)
				.orElseThrow(() -> new Exception("Utente non trovato"));

		return passwordEncoder.matches(rawPassword, utente.getPassword());
	}

}
