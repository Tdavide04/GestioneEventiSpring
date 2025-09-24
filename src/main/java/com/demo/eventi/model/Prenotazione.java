package com.demo.eventi.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPrenotazione;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_evento", nullable = false)
	private Evento evento;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_utente", nullable = false)
	private Utente utente; 
	@Column(name = "posti_prenotati", nullable = false)
	private Integer postiPrenotati;
	@Column(name = "data_creazione", nullable = false, updatable = false)
    private LocalDate dataCreazione;

    @PrePersist
    public void prePersist() {
        this.dataCreazione = LocalDate.now();
    }

    public Prenotazione() {
	}
	public Prenotazione(Long idPrenotazione, Long idUtente, Long idEvento, Integer postiPrenotati,
			LocalDate dataCreazione) {
		super();
		this.idPrenotazione = idPrenotazione;
		this.idUtente = idUtente;
		this.idEvento = idEvento;
		this.postiPrenotati = postiPrenotati;
		this.dataCreazione = dataCreazione;
	}

	public Long getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(Long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getPostiPrenotati() {
		return postiPrenotati;
	}

	public void setPostiPrenotati(Integer postiPrenotati) {
		this.postiPrenotati = postiPrenotati;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
    

}
