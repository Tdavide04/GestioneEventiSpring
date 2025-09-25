package com.demo.eventi.service;

import java.util.List;
import java.util.Optional;

import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.model.Utente;

public interface IPrenotazioneService {
	Prenotazione creaPrenotazione(Prenotazione prenotazione);

	Prenotazione aggiornaPrenotazione(Prenotazione prenotazione) throws Exception;

	void eliminaPrenotazione(Long id) throws Exception;

	Optional<Prenotazione> trovaPerId(Long id);

	List<Prenotazione> trovaPerUtente(Utente utente) throws Exception;
}
