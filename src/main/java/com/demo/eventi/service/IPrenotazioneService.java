package com.demo.eventi.service;

import java.util.List;
import java.util.Optional;

import com.demo.eventi.model.Evento;
import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.model.Utente;

public interface IPrenotazioneService {

	Prenotazione aggiornaPrenotazione(Prenotazione prenotazione) throws Exception;

	void eliminaPrenotazione(Long id) throws Exception;

	Optional<Prenotazione> trovaPerId(Long id);

	List<Prenotazione> trovaPerUtente(Utente utente) throws Exception;

	Prenotazione salvaPrenotazione(Prenotazione prenotazione);

	Prenotazione creaPrenotazione(Evento evento, Utente utente, Integer postiPrenotati);

}
