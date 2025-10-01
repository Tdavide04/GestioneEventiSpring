package com.demo.eventi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.eventi.model.Evento;
import com.demo.eventi.model.Prenotazione;
import com.demo.eventi.model.Utente;
import com.demo.eventi.service.IEventoService;
import com.demo.eventi.service.IPrenotazioneService;
import com.demo.eventi.service.IUtenteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    private final IUtenteService utenteService;
    private final IEventoService eventoService;
    private final IPrenotazioneService prenotazioneService;

    public PrenotazioneController(IUtenteService utenteService, IEventoService eventoService, IPrenotazioneService prenotazioneService) {
        this.utenteService = utenteService;
        this.eventoService = eventoService;
        this.prenotazioneService = prenotazioneService;
    }

    private Utente getLoggedUser(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("loggedUtente");
        if (username == null)
            throw new Exception("User not authenticated");
        return utenteService.trovaPerUsername(username).orElseThrow(() -> new Exception("User not found"));
    }
    
    @GetMapping("/crea-prenotazione")
    public String mostraFormPrenotazione(@RequestParam("eventoId") Long eventoId, Model model) {
        Evento evento = eventoService.trovaPerId(eventoId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventoId));
        model.addAttribute("evento", evento);
        return "prenotazione"; // prenotazione.html
    }

    @PostMapping("/crea-prenotazione")
    public String creaPrenotazione(
            @RequestParam("idEvento") Long idEvento,
            @RequestParam("postiPrenotati") Integer postiPrenotati,
            HttpSession session,
            RedirectAttributes redirectAttrs) {

        try {
            Evento evento = eventoService.trovaPerId(idEvento)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + idEvento));

            Prenotazione prenotazione = prenotazioneService.creaPrenotazione(
                    evento,
                    getLoggedUser(session),
                    postiPrenotati
            );

            if (prenotazione != null) {
                redirectAttrs.addFlashAttribute("success", "Booking created successfully!");
            } else {
                redirectAttrs.addFlashAttribute("error", "Unable to create booking.");
            }
            return "redirect:/prenotazione/mie-prenotazioni";

        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error",
                    "Error while creating booking: " + e.getMessage());
            return "redirect:/prenotazione/mie-prenotazioni";
        }
    }

    @GetMapping("/mie-prenotazioni")
    public String listaPrenotazioni(Model model, HttpSession session) {
        try {
            Utente utente = getLoggedUser(session);
            List<Prenotazione> prenotazioni = prenotazioneService.trovaPerUtente(utente);
            model.addAttribute("prenotazioni", prenotazioni);
            return "mie-prenotazioni";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/aggiorna")
    public String aggiornaPrenotazione(
            @RequestParam Long idPrenotazione,
            @RequestParam Integer postiPrenotati,
            RedirectAttributes redirectAttrs) {

        try {
            Prenotazione prenotazione = prenotazioneService.trovaPerId(idPrenotazione)
                    .orElseThrow(() -> new Exception("Booking not found"));

            prenotazione.setPostiPrenotati(postiPrenotati);
            prenotazioneService.aggiornaPrenotazione(prenotazione);

            redirectAttrs.addFlashAttribute("success", "Booking updated successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error while updating booking: " + e.getMessage());
        }

        return "redirect:/prenotazione/mie-prenotazioni";
    }

    @PostMapping("/elimina")
    public String eliminaPrenotazione(
            @RequestParam Long idPrenotazione,
            RedirectAttributes redirectAttrs) {

        try {
            prenotazioneService.eliminaPrenotazione(idPrenotazione);
            redirectAttrs.addFlashAttribute("success", "Booking deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error while deleting booking: " + e.getMessage());
        }

        return "redirect:/prenotazione/mie-prenotazioni";
    }
}
