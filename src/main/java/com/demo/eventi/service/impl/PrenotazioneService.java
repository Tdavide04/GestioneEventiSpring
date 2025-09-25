package com.demo.eventi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.repository.PrenotazioneRepository;
import com.demo.eventi.service.IPrenotazioneService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrenotazioneService implements IPrenotazioneService {
	
private final PrenotazioneRepository prenotazioneRepository;
	
	public PrenotazioneService(PrenotazioneRepository prenotazioneRepository) {
		this.prenotazioneRepository = prenotazioneRepository;
	}

	@Override
	public Prenotazione creaPrenotazione(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
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
	    prenotazioneRepository.deleteById(id);
	}


	@Override
	public Optional<Prenotazione> trovaPerId(Long id) {
		return prenotazioneRepository.findById(id);
	}
}
