package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Prenotazione;

public interface IPrenotazioneService {
	Prenotazione creaPrenotazione(Prenotazione prenotazione);
	
	Prenotazione aggiornaPrenotazione(Prenotazione prenotazione);
	
	void eliminaPrenotazione(Long id);
	
	Optional<Prenotazione> trovaPerId(Long id);
}
