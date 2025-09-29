package com.demo.eventi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvento;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "descrizione", nullable = false, columnDefinition = "TEXT")
	private String descrizione;
	@Column(name = "posti_totali", nullable = false)
	private Integer postiTotali;
	@Column(name = "posti_occupati", nullable = false)
	private Integer postiOccupati = 0;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;
	@Column(name = "prezzo", nullable = false, precision = 10, scale = 2)
	private BigDecimal prezzo;
	@Column(name = "data_inizio", nullable = false)
	private LocalDate dataInizio;
	@Column(name = "data_fine", nullable = false)
	private LocalDate dataFine;
	@Column(name = "data_creazione", nullable = false, updatable = false)
	private LocalDateTime dataCreazione;
	@OneToMany(mappedBy = "evento")
	private List<Prenotazione> prenotazioni;
	
	@PrePersist
	public void prePersist() {
		dataCreazione = LocalDateTime.now();
	}
	public Evento() {

	}
	public Evento(String nome, String descrizione, Integer postiTotali,
			Categoria categoria, BigDecimal prezzo, LocalDate dataInizio, LocalDate dataFine) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.postiTotali = postiTotali;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	public Evento(Long idEvento, String nome, String descrizione, Integer postiTotali, Integer postiOccupati,
			Categoria categoria, BigDecimal prezzo, LocalDate dataInizio, LocalDate dataFine,
			LocalDateTime dataCreazione, List<Prenotazione> prenotazioni) {
		super();
		this.idEvento = idEvento;
		this.nome = nome;
		this.descrizione = descrizione;
		this.postiTotali = postiTotali;
		this.postiOccupati = postiOccupati;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.dataCreazione = dataCreazione;
		this.prenotazioni = prenotazioni;
	}
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getPostiTotali() {
		return postiTotali;
	}
	public void setPostiTotali(Integer postiTotali) {
		this.postiTotali = postiTotali;
	}
	public Integer getPostiOccupati() {
		return postiOccupati;
	}
	public void setPostiOccupati(Integer postiOccupati) {
		this.postiOccupati = postiOccupati;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	public LocalDate getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDate getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}
	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	@Transient
	public Integer getPostiDisponibili() {
	    return this.postiTotali - this.postiOccupati;
	}
	
}
