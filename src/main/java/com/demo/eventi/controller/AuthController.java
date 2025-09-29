package com.demo.eventi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.eventi.model.Utente;
import com.demo.eventi.service.IUtenteService;

@Controller
public class AuthController {

    private final IUtenteService utenteService;

    public AuthController(IUtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @PostMapping("/register")
    public String registerUtente(@ModelAttribute Utente utente,
                               RedirectAttributes redirectAttrs) {
        try {
            utenteService.registraUtente(utente);
            redirectAttrs.addFlashAttribute("success", "Registrazione completata! Ora puoi accedere.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session,
                            RedirectAttributes redirectAttrs) {
        try {
            if (utenteService.verificaPassword(username, password)) {
                Utente utente = utenteService.trovaPerUsername(username).orElseThrow();
                session.setAttribute("loggedUtente", username);
                session.setAttribute("loggedRuolo", utente.getRuolo());
                
                // DEBUG
                System.out.println("DEBUG: Utente loggato: " + username + " | Ruollo: " + utente.getRuolo());

                return "redirect:/evento/evento-lista";
            } else {
                redirectAttrs.addFlashAttribute("error", "Credenziali errate");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logoutUtente(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate();
        redirectAttrs.addFlashAttribute("success", "Logout effettuato con successo.");
        return "redirect:/login";
    }
}
