package com.demo.eventi.controller;

import com.demo.eventi.model.Ruolo;
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
        Ruolo ruolo = (Ruolo) session.getAttribute("loggedRuolo");

        if (username == null || ruolo == null || ruolo != Ruolo.ADMIN) {
            redirectAttrs.addFlashAttribute("error", "Access denied");
            return "redirect:/evento/attivi";
        }

        model.addAttribute("utenti", utenteService.trovaTutti());
        return "gestione-utente";
    }

    @GetMapping("/modifica")
    public String mostraFormModifica(Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        String username = (String) session.getAttribute("loggedUtente");
        Ruolo ruolo = (Ruolo) session.getAttribute("loggedRuolo");

        if (username == null || ruolo == null || ruolo != Ruolo.ADMIN) {
            redirectAttrs.addFlashAttribute("error", "Access denied");
            return "redirect:/evento/attivi";
        }

        Optional<Utente> utenteOpt = utenteService.trovaPerUsername(username);
        if (utenteOpt.isPresent()) {
            model.addAttribute("utente", utenteOpt.get());
            return "gestione-utente";
        } else {
            redirectAttrs.addFlashAttribute("error", "User not found");
            return "redirect:/utenti";
        }
    
    }
    
    @PostMapping("/aggiorna")
    public String aggiornaUtente(
            @RequestParam Long idUtente,
            @RequestParam String email,
            @RequestParam Ruolo ruolo,
            RedirectAttributes redirectAttrs
    ) {
        try {
            Optional<Utente> utenteOpt = utenteService.trovaPerId(idUtente);
            if (utenteOpt.isPresent()) {
                Utente utente = utenteOpt.get();
                utente.setEmail(email);
                utente.setRuolo(ruolo);
                utenteService.aggiornaUtente(utente);
                redirectAttrs.addFlashAttribute("success", "The user has been updated successfully");
            } else {
                redirectAttrs.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/utenti";
    }

}
