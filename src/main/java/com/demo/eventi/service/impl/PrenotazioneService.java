package com.demo.eventi.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.eventi.model.Evento;
import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.model.Utente;
import com.demo.eventi.repository.PrenotazioneRepository;
import com.demo.eventi.repository.UtenteRepository;
import com.demo.eventi.service.IPrenotazioneService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrenotazioneService implements IPrenotazioneService {

	private final PrenotazioneRepository prenotazioneRepository;
	private final UtenteRepository utenteRepository;

	public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, UtenteRepository utenteRepository) {
		this.prenotazioneRepository = prenotazioneRepository;
		this.utenteRepository = utenteRepository;
	}

	@Override
	public Prenotazione salvaPrenotazione(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}

	@Override
	public Prenotazione creaPrenotazione(Evento evento, Utente utente, Integer postiPrenotati) throws Exception {
		if(evento.getPostiDisponibili() > 0 && evento.getPostiDisponibili() - postiPrenotati >= 0) {
			if (evento.getDataFine().isAfter(LocalDate.now())) {
				Prenotazione prenotazione = new Prenotazione(evento, utente, postiPrenotati);
				try {
					evento.setPostiOccupati(evento.getPostiOccupati() + postiPrenotati);
					salvaPrenotazione(prenotazione);
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			} else {
				throw new Exception("L'evento è gia terminato");
			}
			
		}
		return null;
	}
		
	@Override
	public Prenotazione aggiornaPrenotazione(Prenotazione prenotazione) throws Exception {
		if (!prenotazioneRepository.existsById(prenotazione.getIdPrenotazione())) {
			throw new Exception("Prenotazione non trovata per ID: " + prenotazione.getIdPrenotazione());
		}
		return prenotazioneRepository.save(prenotazione);
	}

	@Override
	public void eliminaPrenotazione(Long id) throws Exception {
		if (!prenotazioneRepository.existsById(id)) {
			throw new Exception("Prenotazione non trovata per ID: " + id);
		}
		Prenotazione prenotazione = prenotazioneRepository.getById(id);
		prenotazione.getEvento().setPostiOccupati(prenotazione.getEvento().getPostiOccupati() - prenotazione.getPostiPrenotati());
		prenotazioneRepository.deleteById(id);
	}

	@Override
	public Optional<Prenotazione> trovaPerId(Long id) {
		return prenotazioneRepository.findById(id);
	}

	@Override
	public List<Prenotazione> trovaPerUtente(Utente utente) throws Exception {
		if (!utenteRepository.existsById(utente.getIdUtente())) {
			throw new Exception("Utente non trovato con ID: " + utente.getIdUtente());
		}
		return prenotazioneRepository.findByUtente(utente);
	}
	
}
