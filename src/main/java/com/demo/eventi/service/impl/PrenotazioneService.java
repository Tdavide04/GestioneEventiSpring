package com.demo.eventi.service.impl;

import java.util.Optional;

import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.repository.PrenotazioneRepository;
import com.demo.eventi.service.IPrenotazioneService;

public class PrenotazioneService implements IPrenotazioneService {
	
private final PrenotazioneRepository prenotazioneRepository;
	
	public PrenotazioneService(PrenotazioneRepository prenotazioneRepository) {
		this.prenotazioneRepository = prenotazioneRepository;
	}

	@Override
	public Prenotazione creaPrenotazione(Prenotazione prenotazione) {
		// vedere se servono controlli
		return prenotazioneRepository.save(prenotazione);
	}

	@Override
	public Optional<Prenotazione> trovaPerId(Long id) {
		return prenotazioneRepository.findById(id);
	}

}
