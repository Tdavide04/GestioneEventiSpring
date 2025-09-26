package com.demo.eventi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * Metodo eseguito prima che il controller venga chiamato. Può decidere se la
	 * richiesta deve continuare o essere bloccata.
	 *
	 * @param request  la richiesta HTTP del client
	 * @param response la risposta HTTP
	 * @param handler  il controller (o handler) che gestirebbe la richiesta
	 * @return true se la richiesta può continuare, false se deve essere bloccata
	 * @throws Exception in caso di errori durante l’elaborazione
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// Recupera la sessione corrente senza crearne una nuova (false)
		HttpSession session = request.getSession(false);
		// Controlla se l’utente è loggato: esiste una sessione e contiene "loggedUtente"
		boolean loggedIn = (session != null && session.getAttribute("loggedUtente") != null);

		// Ottiene l’URI della richiesta (es: /login, /tasks, /css/style.css)
		String uri = request.getRequestURI();

		// Rotte libere: login, registrazione e cartella CSS
		// Queste rotte possono essere visitate anche senza essere loggati
		if (uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/css") || uri.startsWith("/images")) {
			return true;
		}

		if (!loggedIn) {
			// Reindirizza l’utente alla pagina di login
			response.sendRedirect("/login");
			return false;
		}

		return true;
	}
}
