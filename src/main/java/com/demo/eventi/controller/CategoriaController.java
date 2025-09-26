package com.demo.eventi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.eventi.model.Categoria;
import com.demo.eventi.model.Ruolo;
import com.demo.eventi.service.ICategoriaService;
import com.demo.eventi.service.IUtenteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final ICategoriaService categoriaService;
    private final IUtenteService utenteService;

    public CategoriaController(ICategoriaService categoriaService, IUtenteService utenteService) {
        this.categoriaService = categoriaService;
        this.utenteService = utenteService;
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedUtente");
        if (username == null) return false;

        return utenteService.trovaPerUsername(username)
                .map(u -> u.getRuolo() == Ruolo.ADMIN)
                .orElse(false);
    }


    @GetMapping("/nuova")
    public String mostraFormCategoria(Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!isAdmin(session)) {
            redirectAttrs.addFlashAttribute("error", "Accesso negato: solo per amministratori.");
            return "redirect:/evento/evento-lista";
        }

        model.addAttribute("categoria", new Categoria());
        return "categoria-form";
    }

    @PostMapping("/crea")
    public String creaCategoria(@ModelAttribute Categoria categoria, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!isAdmin(session)) {
            redirectAttrs.addFlashAttribute("error", "Accesso negato.");
            return "redirect:/evento/evento-lista";
        }

        try {
            categoriaService.creaCategoria(categoria);
            redirectAttrs.addFlashAttribute("success", "Categoria creata con successo!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Errore durante la creazione della categoria.");
        }

        return "redirect:/evento/nuovo";
    }
}

