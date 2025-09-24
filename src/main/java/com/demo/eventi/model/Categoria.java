package com.demo.eventi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCategoria;
	@Column(name = "nome", nullable = false, unique = true, length = 30)
	private String nome;
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
	@OneToMany(mappedBy = "categoria")
	private List<Evento> eventi = new ArrayList<>();
	public Categoria(Long idCategoria, String nome, String descrizione) {
		super();
		this.idCategoria = idCategoria;
		this.nome = nome;
		this.descrizione = descrizione;
	}
	public Categoria() {
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
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
	public List<Evento> getEventi() {
		return eventi;
	}
	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}
	
}
