package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Prenotazione;

public interface IPrenotazioneService {
	Prenotazione creaPrenotazione(Prenotazione prenotazione);
	
	Optional<Prenotazione> trovaPerId(Long id);
}
