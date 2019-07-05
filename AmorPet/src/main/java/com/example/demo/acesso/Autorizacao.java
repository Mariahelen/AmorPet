package com.example.demo.acesso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.model.Usuario;

public class Autorizacao implements HandlerInterceptor {

	private static final boolean CONTROLAR_ACESSO = true;

	private static final String[] RECURSOS_LIVRES = { "/home", "/termos", "/navegacao", "/quem-somos", "/login",
			"/cadastro", "/adotar", "/descricao-animal" };

	private static final String[] RECURSOS_LIVRES_ESTILIZACAO = { ".css", ".js", ".png", ".jpg" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!CONTROLAR_ACESSO) {
			return true;
		}

		// Para acessar qualquer pagina dessa aplicação, o usuário precisa estar
		// autenticado
		for (String recurso : RECURSOS_LIVRES) {
			if (request.getRequestURL().toString().endsWith(recurso)) {
				return true;
			}
		}
		for(String estilos : RECURSOS_LIVRES_ESTILIZACAO) {
			if(request.getRequestURL().toString().endsWith(estilos)) {
				return true;
			}
		}

//		Class<?> classe = request.getSession().getAttribute("usuarioLogado").getClass();
//		boolean isRoleUser = classe.getField("role").toString().endsWith("USER");

		if (request.getSession().getAttribute("usuarioLogado") == null) {
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		} else {
			return true;
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
