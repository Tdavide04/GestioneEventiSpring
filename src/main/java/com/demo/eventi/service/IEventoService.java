package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Evento;

public interface IEventoService {
	Evento creaEvento(Evento evento);
	
	Evento aggiornaEvento(Evento evento);
	
	void eliminaEvento(Long id);
	
	Optional<Evento> trovaPerId(Long id);
	
}
