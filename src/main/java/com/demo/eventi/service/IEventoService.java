package com.demo.eventi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.demo.eventi.model.Evento;

public interface IEventoService {
	Evento creaEvento(Evento evento);

	Evento aggiornaEvento(Evento evento) throws Exception;

	void eliminaEvento(Long id) throws Exception;

	Optional<Evento> trovaPerId(Long id);
	
	Optional<Evento> trovaPerNome(String nome);

	List<Evento> trovaDisponibili();

	List<Evento> trovaPerDataCreazione(LocalDate data);
	
	List<Evento> trovaEventiScaduti();

	List<Evento> trovaEventiInScadenza();

	List<Evento> trovaEventiRecenti();
}
