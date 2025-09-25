package com.demo.eventi.repository;

import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.model.Utente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	List<Prenotazione> findByUtente(Utente utente);
}
