package com.demo.eventi.service.impl;

import java.util.List;
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
	public Utente aggiornaUtente(Utente utente) throws Exception {
		Optional<Utente> utenteEsistenteOpt = utenteRepository.findById(utente.getIdUtente());
		if (utenteEsistenteOpt.isEmpty()) {
			throw new Exception("Utente non trovato");
		}

		Utente utenteEsistente = utenteEsistenteOpt.get();

		if (!utenteEsistente.getUsername().equals(utente.getUsername()) &&
			utenteRepository.existsByUsername(utente.getUsername())) {
			throw new Exception("Username già in uso");
		}

		if (!utenteEsistente.getEmail().equals(utente.getEmail()) &&
			utenteRepository.existsByEmail(utente.getEmail())) {
			throw new Exception("Email già in uso");
		}

		utenteEsistente.setUsername(utente.getUsername());
		utenteEsistente.setEmail(utente.getEmail());

		if (!passwordEncoder.matches(utente.getPassword(), utenteEsistente.getPassword())) {
			utenteEsistente.setPassword(passwordEncoder.encode(utente.getPassword()));
		}

		utenteEsistente.setRuolo(utente.getRuolo());

		return utenteRepository.save(utenteEsistente);
	}

	@Override
	public Optional<Utente> trovaPerId(Long idUtente) {
		return utenteRepository.findById(idUtente);
	}


	@Override
	public Optional<Utente> trovaPerUsername(String username) {
		return utenteRepository.findByUsername(username);
	}
	
	@Override
	public List<Utente> trovaTutti() {
		return utenteRepository.findAll();
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
