package com.example.demo.acesso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.example.demo.model.Usuario;

public class Autorizacao implements HandlerInterceptor {

	private static final boolean CONTROLAR_ACESSO = true;

	private static final String[] RECURSOS_LIVRES = { "/home", "/termos", "/navegacao", "/quem-somos", "/login",
			"/cadastro", "/adotar", "/descricao-animal",
			"https://fonts.googleapis.com/css?family=Roboto:700,400&subset=cyrillic,latin,greek,vietnamese"};

	private static final String RECURSOS_USUARIOS_NORMAL = "/user/";

	private static final String RECURSOS_USUARIOS_ADM = "/adm/";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// libera tudo da pasta static
		if(handler instanceof ResourceHttpRequestHandler) {
			return true;
		}
		if (!CONTROLAR_ACESSO) {
			return true;
		}

		// Para acessar qualquer pagina dessa aplicação, o usuário precisa estar
		// autenticado
		for (String recurso : RECURSOS_LIVRES) {
			if (request.getRequestURL().toString().contains(recurso)
					&& !request.getRequestURL().toString().contains(RECURSOS_USUARIOS_ADM)) {
				return true;
			}
		}
		

		Usuario usuario =  (Usuario) request.getSession().getAttribute("usuarioLogado");
		
		if(request.getSession().getAttribute("usuarioLogado") == null) {
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		} else {
			if(request.getRequestURL().toString().contains(RECURSOS_USUARIOS_NORMAL)
					&& usuario.getRole().endsWith("USER")) {
				
				return true;
			}else if(request.getRequestURL().toString().contains(RECURSOS_USUARIOS_ADM)
					| request.getRequestURL().toString().contains(RECURSOS_USUARIOS_NORMAL)
					&& usuario.getRole().endsWith("ADMIN")) {
				
				return true;
			}
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		}

	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		System.out.println(" >>> INFO:: Interceptor pós chamada <<< ");
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception exception) throws Exception {
//		System.out.println(" >>> INFO:: Interceptor depois de completado <<< ");
//	}
}
