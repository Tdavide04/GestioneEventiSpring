package com.demo.eventi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.eventi.model.Categoria;
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
	
	@GetMapping("/attivi")
    public String listaEventiDisponibili(Model model, HttpSession session) {
        try {
        	getLoggedUser(session);
            List<Evento> eventi = eventoService.trovaDisponibili();
            model.addAttribute("eventi", eventi);
            model.addAttribute("loggedRuolo", session.getAttribute("loggedRuolo"));
            return "evento-lista";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }
	
	@GetMapping("/scaduti")
	public String listaEventiScaduti(Model model, HttpSession session) {
	    try {
	        getLoggedUser(session);
	        List<Evento> eventi = eventoService.trovaEventiScaduti();
	        model.addAttribute("eventi", eventi);
	        return "evento-lista";
	    } catch (Exception e) {
	        return "redirect:/login";
	    }
	}

	@GetMapping("/in-scadenza")
	public String listaEventiInScadenza(Model model, HttpSession session) {
	    try {
	        getLoggedUser(session);
	        List<Evento> eventi = eventoService.trovaEventiInScadenza();
	        model.addAttribute("eventi", eventi);
	        return "evento-lista";
	    } catch (Exception e) {
	        return "redirect:/login";
	    }
	}

	@GetMapping("/recenti")
	public String listaEventiRecenti(Model model, HttpSession session) {
	    try {
	        getLoggedUser(session);
	        List<Evento> eventi = eventoService.trovaEventiRecenti();
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
    
    //TODO DA USARE PER BARRA DI RICERCA
    @GetMapping("/cerca")
    public String cercaPerNome(@RequestParam("nome") String nome, Model model, HttpSession session) {
        try {
            getLoggedUser(session);
            eventoService.trovaPerNome(nome).ifPresentOrElse(
                evento -> model.addAttribute("eventi", List.of(evento)),
                () -> model.addAttribute("error", "Nessun evento trovato con il nome: " + nome)
            );
            return "evento-lista";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @PostMapping
    public String creaEvento(@ModelAttribute Evento evento, 
                            @RequestParam("categoriaId") Long categoriaId,
                            HttpSession session, 
                            RedirectAttributes redirectAttrs) {
    	
    	// DEBUG: Stampa quello che arriva
        System.out.println("=== DEBUG EVENTO ===");
        System.out.println("Nome: " + evento.getNome());
        System.out.println("Descrizione: " + evento.getDescrizione());
        System.out.println("Posti totali: " + evento.getPostiTotali());
        System.out.println("Prezzo: " + evento.getPrezzo());
        System.out.println("Data inizio: " + evento.getDataInizio());
        System.out.println("Data fine: " + evento.getDataFine());
        System.out.println("CategoriaId parameter: " + categoriaId);
        System.out.println("Categoria object: " + evento.getCategoria());
        
        try {
            Categoria categoria = categoriaService.trovaPerId(categoriaId)
                                 .orElseThrow(() -> new Exception("Categoria non valida"));
            evento.setCategoria(categoria);

            eventoService.creaEvento(evento);
            redirectAttrs.addFlashAttribute("success", "Evento creato con successo!");
            return "redirect:/evento/evento-lista";
        } catch (Exception e) {
        	e.printStackTrace(); // DEBUG: Per vedere l'errore completo
            redirectAttrs.addFlashAttribute("error", "Errore durante la creazione dell'evento: " + e.getMessage());
            return "redirect:/evento/nuovo";
        }
    }
    
    @PostMapping("/test")
    @ResponseBody
    public String testEvento(@ModelAttribute Evento evento, 
                            @RequestParam Map<String, String> allParams) {
        return "Evento: " + evento.toString() + "\nParametri: " + allParams.toString();
    }
}
