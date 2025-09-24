package com.demo.eventi.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUtente;
	@Column(name = "username", nullable = false, unique = true)
	@Size(min = 3, max = 25, message = "L'username deve essere compreso tra i 3 e i 25 caratteri")
	private String username;
	@Column(name = "email", nullable = false, unique = true)
	@Pattern(
	        regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", 
	        message = "Email non valida"
	    )
	private String email;
	@Column(name = "password", nullable = false)
	@Size(min = 8, message = "La password deve essere composta da minimo 5 caratteri")
	private String password;
	@Column(name = "ruolo", nullable = false)
	@Enumerated(EnumType.STRING)
	private Ruolo ruolo;
	@OneToMany(mappedBy = "utente")
	private List<Prenotazione> prenotazioni = new ArrayList<>();
	public Utente() {
	}
	public Utente(Long idUtente,
			@Size(min = 3, max = 25, message = "L'username deve essere compreso tra i 3 e i 25 caratteri") String username,
			@Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "Email non valida") String email,
			@Size(min = 8, message = "La password deve essere composta da minimo 5 caratteri") String password,
			Ruolo ruolo) {
		super();
		this.idUtente = idUtente;
		this.username = username;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Ruolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
}
