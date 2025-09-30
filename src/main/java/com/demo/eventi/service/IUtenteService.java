package com.demo.eventi.service;

import java.util.List;
import java.util.Optional;

import com.demo.eventi.model.Utente;

public interface IUtenteService {
	Utente registraUtente(Utente utente) throws Exception;
	
	Utente aggiornaUtente(Utente utente) throws Exception;

	Optional<Utente> trovaPerId(Long id);

	Optional<Utente> trovaPerUsername(String username);
	
	List<Utente> trovaTutti();

	boolean esisteUsername(String username);

	boolean esisteEmail(String email);

	boolean verificaPassword(String username, String rawPassword) throws Exception;
}
