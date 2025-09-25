package com.demo.eventi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Classe di configurazione Spring MVC.
 * Implementa WebMvcConfigurer per personalizzare il comportamento di Spring MVC
 * senza sovrascrivere la configurazione automatica di Spring Boot.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
     * Metodo per registrare gli interceptor.
     * Gli interceptor permettono di intercettare le richieste HTTP
     * prima che arrivino al controller e/o dopo l’esecuzione del controller.
     *
     * @param registry oggetto su cui registrare i tuoi interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// Registra il nostro AuthInterceptor
        // Questo interceptor controllerà che l’utente sia loggato prima di accedere alle rotte protette
        registry.addInterceptor(new AuthInterceptor());
    }

	/*
	 * Metodo per mappare direttamente URL a view o redirect senza scrivere un controller.
     * È utile per redirect o pagine statiche.
     * 
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * registry.addRedirectViewController("/", null); }
	 */
}
