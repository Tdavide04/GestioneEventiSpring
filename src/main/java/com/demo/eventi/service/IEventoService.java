package com.demo.eventi.service;

import java.util.Optional;

import com.demo.eventi.model.Evento;

public interface IEventoService {
	Evento creaEvento(Evento evento);
	
	Optional<Evento> trovaPerId(Long id);
}
