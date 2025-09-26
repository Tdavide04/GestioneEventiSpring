package com.demo.eventi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.eventi.model.Evento;
import com.demo.eventi.model.Utente;
import com.demo.eventi.service.ICategoriaService;
import com.demo.eventi.service.IEventoService;
import com.demo.eventi.service.IUtenteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/evento")
public class EventoController {
	private final IEventoService eventoService;
	private final IUtenteService utenteService;
	private final ICategoriaService categoriaService;
	
	public EventoController(IEventoService eventoService, IUtenteService utenteService, ICategoriaService categoriaService) {
		this.eventoService = eventoService;
		this.utenteService = utenteService;
		this.categoriaService = categoriaService;
	}
	
	private Utente getLoggedUser(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("loggedUtente");
        if (username == null) throw new Exception("Utente non autenticato");
        return utenteService.trovaPerUsername(username)
                .orElseThrow(() -> new Exception("Utente non trovato"));
    }
	
	@GetMapping("/evento-lista")
    public String listaEventiDisponibili(Model model, HttpSession session) {
        try {
        	Utente utente = getLoggedUser(session);
            List<Evento> eventi = eventoService.trovaDisponibili();
            model.addAttribute("eventi", eventi);
            return "evento-lista";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/nuovo")
    public String mostraCreazioneEventoForm(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("categorie", categoriaService.trovaTutte());
        return "evento-form";
    }
    
    @PostMapping
    public String creaEvento(@ModelAttribute Evento evento, HttpSession session, RedirectAttributes redirectAttrs) {
        try {
            eventoService.creaEvento(evento);
            redirectAttrs.addFlashAttribute("success", "Evento creato con successo!");
            return "redirect:/evento";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Errore durante la creazione dell'evento.");
            return "redirect:/evento";
        }
    }
}
