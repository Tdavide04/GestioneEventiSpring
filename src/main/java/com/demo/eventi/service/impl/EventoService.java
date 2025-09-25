package com.demo.eventi.service.impl;

import java.util.Optional;

import com.demo.eventi.model.Evento;
import com.demo.eventi.repository.EventoRepository;
import com.demo.eventi.service.IEventoService;

public class EventoService implements IEventoService {
	
private final EventoRepository eventoRepository;
	
	public EventoService(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}

	@Override
	public Evento creaEvento(Evento evento) {
		// vedere se servono controlli
		return eventoRepository.save(evento);
	}
	
	@Override
	public Evento aggiornaEvento(Evento evento) {
		return eventoRepository.save(evento);
	}

	@Override
	public void eliminaEvento(Long id) {
		eventoRepository.deleteById(id);
		
	}

	@Override
	public Optional<Evento> trovaPerId(Long id) {
		return eventoRepository.findById(id);
	}
}
