package com.demo.eventi.repository;

import com.demo.eventi.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	Optional<Evento> findByNome(String nome);
	
	List<Evento> findByDataFineAfter(LocalDate data);
	// Restituisce tutti gli eventi la cui data di fine è successiva alla data specificata.

	List<Evento> findByDataCreazione(LocalDate data);
	// Restituisce tutti gli eventi creati esattamente nella data specificata.
	// ATTENZIONE: se il campo dataCreazione è LocalDateTime, questo metodo potrebbe non funzionare correttamente per una data intera.

	List<Evento> findByDataFineBetween(LocalDate oggi, LocalDate limite);
	// Restituisce tutti gli eventi con data di fine compresa tra due date.

	List<Evento> findByDataCreazioneAfter(LocalDateTime limite);
	// Restituisce tutti gli eventi creati dopo una certa data/ora.

	List<Evento> findByDataFineGreaterThanEqual(LocalDate data);
	// Restituisce tutti gli eventi con data di fine **uguale o successiva** alla data specificata.

	List<Evento> findByDataFineBefore(LocalDate data);
	// Restituisce tutti gli eventi con data di fine precedente alla data specificata.

	List<Evento> findByDataCreazioneBetween(LocalDateTime start, LocalDateTime end);
	// Restituisce tutti gli eventi creati in un intervallo temporale definito da due LocalDateTime.
}
