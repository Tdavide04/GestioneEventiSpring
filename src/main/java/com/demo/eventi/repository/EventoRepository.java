package com.demo.eventi.repository;

import com.demo.eventi.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	// per visualizzare gli eventi ancora disponibili
	List<Evento> findByDataFineAfter(LocalDate data);

	// per visualizzare gli eventi creati in una certa data
	List<Evento> findByDataCreazione(LocalDate data);
}
