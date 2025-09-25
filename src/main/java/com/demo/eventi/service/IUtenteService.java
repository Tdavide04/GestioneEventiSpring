package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Utente;

public interface IUtenteService {
	Utente registraUtente(Utente utente) throws Exception;

	Optional<Utente> trovaPerUsername(String username);

	boolean esisteUsername(String username);

	boolean esisteEmail(String email);

	boolean verificaPassword(String username, String rawPassword) throws Exception;
}
