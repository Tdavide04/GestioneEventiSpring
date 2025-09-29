package com.demo.eventi.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.eventi.model.Evento;
import com.demo.eventi.repository.EventoRepository;
import com.demo.eventi.service.IEventoService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EventoService implements IEventoService {

	private final EventoRepository eventoRepository;

	public EventoService(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}

	@Override
	public Evento creaEvento(Evento evento) {
		return eventoRepository.save(evento);
	}

	@Override
	public Evento aggiornaEvento(Evento evento) throws Exception {
		if (!eventoRepository.existsById(evento.getIdEvento())) {
			throw new Exception("Evento non trovato per l'ID: " + evento.getIdEvento());
		}
		return eventoRepository.save(evento);
	}

	@Override
	public void eliminaEvento(Long id) throws Exception {
		if (!eventoRepository.existsById(id)) {
			throw new Exception("Evento non trovato per ID: " + id);
		}
		eventoRepository.deleteById(id);
	}

	@Override
	public Optional<Evento> trovaPerId(Long id) {
		return eventoRepository.findById(id);
	}
	
	@Override
	public Optional<Evento> trovaPerNome(String nome) {
		return eventoRepository.findByNome(nome);
	}

	@Override
	public List<Evento> trovaDisponibili() {
		return eventoRepository.findByDataFineAfter(LocalDate.now());
	}

    @Override
    public List<Evento> trovaPerDataCreazione(LocalDate data) {
        LocalDateTime startOfDay = data.atStartOfDay();
        LocalDateTime endOfDay = data.atTime(LocalTime.MAX);
        return eventoRepository.findByDataCreazioneBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Evento> trovaEventiScaduti() {
        return eventoRepository.findByDataFineBefore(LocalDate.now());
    }

    @Override
    public List<Evento> trovaEventiInScadenza() {
        LocalDate oggi = LocalDate.now();
        LocalDate limite = oggi.plusDays(3);
        return eventoRepository.findByDataFineBetween(oggi, limite);
    }

    @Override
    public List<Evento> trovaEventiRecenti() {
        LocalDateTime limite = LocalDateTime.now().minusDays(3);
        return eventoRepository.findByDataCreazioneAfter(limite);
    }
}
