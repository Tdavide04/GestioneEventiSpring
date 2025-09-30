package com.demo.eventi.controller;

import com.demo.eventi.model.Utente;
import com.demo.eventi.service.IUtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/utenti")
public class UtenteController {

    private final IUtenteService utenteService;

    public UtenteController(IUtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public String listaUtenti(Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        String username = (String) session.getAttribute("loggedUtente");
        String ruolo = (String) session.getAttribute("loggedRuolo");

        if (username == null || ruolo == null || !ruolo.equals("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "Accesso non autorizzato");
            return "redirect:/evento/attivi";
        }

        model.addAttribute("utenti", utenteService.trovaTutti());
        return "utenti-lista";
    }

    @GetMapping("/modifica/{id}")
    public String mostraFormModifica(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        String username = (String) session.getAttribute("loggedUtente");
        String ruolo = (String) session.getAttribute("loggedRuolo");

        if (username == null || ruolo == null || !ruolo.equals("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "Accesso non autorizzato");
            return "redirect:/evento/attivi";
        }

        Optional<Utente> utenteOpt = utenteService.trovaPerId(id);
        if (utenteOpt.isPresent()) {
            model.addAttribute("utente", utenteOpt.get());
            return "utenti-modifica";
        } else {
            redirectAttrs.addFlashAttribute("error", "Utente non trovato");
            return "redirect:/utenti";
        }
    }

    @PostMapping("/aggiorna")
    public String aggiornaUtente(@ModelAttribute Utente utente,
                                 HttpSession session,
                                 RedirectAttributes redirectAttrs) {
        String username = (String) session.getAttribute("loggedUtente");
        String ruolo = (String) session.getAttribute("loggedRuolo");

        if (username == null || ruolo == null || !ruolo.equals("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "Accesso non autorizzato");
            return "redirect:/evento/attivi";
        }

        try {
            utenteService.aggiornaUtente(utente);
            redirectAttrs.addFlashAttribute("success", "Utente aggiornato con successo");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/utenti";
    }
}
