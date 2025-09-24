package com.demo.eventi.repository;

import com.demo.eventi.model.Prenotazione;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	
}

