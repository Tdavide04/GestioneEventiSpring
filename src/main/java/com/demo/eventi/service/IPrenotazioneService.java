package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Prenotazione;

public interface IPrenotazioneService {
	Prenotazione creaPrenotazione(Prenotazione prenotazione);
	
	Prenotazione aggiornaPrenotazione(Prenotazione prenotazione) throws Exception;
	
	void eliminaPrenotazione(Long id) throws Exception;
	
	Optional<Prenotazione> trovaPerId(Long id);
}
