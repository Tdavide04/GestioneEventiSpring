package com.demo.eventi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public EventoController(IEventoService eventoService, IUtenteService utenteService,
            ICategoriaService categoriaService) {
        this.eventoService = eventoService;
        this.utenteService = utenteService;
        this.categoriaService = categoriaService;
    }

    private Utente getLoggedUser(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("loggedUtente");
        if (username == null)
            throw new Exception("User not authenticated");
        return utenteService.trovaPerUsername(username).orElseThrow(() -> new Exception("User not found"));
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
            model.addAttribute("loggedRuolo", session.getAttribute("loggedRuolo"));
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
            model.addAttribute("loggedRuolo", session.getAttribute("loggedRuolo"));
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
            model.addAttribute("loggedRuolo", session.getAttribute("loggedRuolo"));
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

    @GetMapping("/cerca")
    public String cercaPerNome(@RequestParam("nome") String nome, Model model, HttpSession session) {
        try {
            getLoggedUser(session);
            eventoService.trovaPerNome(nome).ifPresentOrElse(
                evento -> model.addAttribute("eventi", List.of(evento)),
                () -> model.addAttribute("error", "No event found with the name: " + nome)
            );
            return "evento-lista";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @PostMapping
    public String creaEvento(@ModelAttribute Evento evento, @RequestParam("categoriaId") Long categoriaId,
            HttpSession session, RedirectAttributes redirectAttrs) {
        try {
            Categoria categoria = categoriaService.trovaPerId(categoriaId)
                    .orElseThrow(() -> new Exception("Invalid category"));
            evento.setCategoria(categoria);

            eventoService.creaEvento(evento);
            redirectAttrs.addFlashAttribute("success", "Event created successfully!");
            return "redirect:/evento/attivi";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error creating event: " + e.getMessage());
            return "redirect:/evento/nuovo";
        }
    }

    @GetMapping("/modifica")
    public String mostraFormModifica(@RequestParam("id") Long id, Model model, HttpSession session,
            RedirectAttributes redirectAttrs) {
        try {
            getLoggedUser(session);
            Evento evento = eventoService.trovaPerId(id).orElseThrow(() -> new Exception("Event not found"));
            model.addAttribute("evento", evento);
            model.addAttribute("categorie", categoriaService.trovaTutte());
            return "evento-form";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error loading event: " + e.getMessage());
            return "redirect:/evento/attivi";
        }
    }

    @PostMapping("/aggiorna")
    public String aggiornaEvento(@ModelAttribute Evento evento, @RequestParam("categoriaId") Long categoriaId,
            HttpSession session, RedirectAttributes redirectAttrs) {
        try {
            getLoggedUser(session);
            Categoria categoria = categoriaService.trovaPerId(categoriaId)
                    .orElseThrow(() -> new Exception("Invalid category"));
            evento.setCategoria(categoria);

            eventoService.aggiornaEvento(evento);
            redirectAttrs.addFlashAttribute("success", "Event updated successfully!");
            return "redirect:/evento/attivi";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error updating event: " + e.getMessage());
            return "redirect:/evento/modifica?id=" + evento.getIdEvento();
        }
    }

    @GetMapping("/elimina")
    public String eliminaEvento(@RequestParam("id") Long id, HttpSession session, RedirectAttributes redirectAttrs) {
        try {
            getLoggedUser(session);
            eventoService.eliminaEvento(id);
            redirectAttrs.addFlashAttribute("success", "Event deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error deleting event: " + e.getMessage());
        }
        return "redirect:/evento/attivi";
    }
}
