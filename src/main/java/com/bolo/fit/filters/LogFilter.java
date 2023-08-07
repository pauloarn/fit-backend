package com.bolo.fit.filters;

import com.bolo.fit.enums.LogContextEnum;
import com.bolo.fit.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class LogFilter extends OncePerRequestFilter {

    private final static String REQUEST_TIME = "requestTime";
    public static final String CD_TRANSACAO = "cd-transacao";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        beforeRequest(request);
        filterChain.doFilter(request, response);
        afterRequest(request);
    }

    private void beforeRequest(HttpServletRequest request) {
        configLogger(request);
        printQueryParam(request);
    }

    private void afterRequest(HttpServletRequest request) {
        try {
            long startTime = (long) request.getAttribute(REQUEST_TIME);
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            log.info(String.format("Requisição terminada com %d ms.", executeTime));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        LogUtils.limparContextoMDC(LogContextEnum.API_CONTEXT);
    }

    private void configLogger(HttpServletRequest request) {
        request.setAttribute(REQUEST_TIME, System.currentTimeMillis());
        String req = String.format("( %s ) %s", request.getMethod(), request.getRequestURI());

        LogUtils.adicionarCdTransacao(request.getHeader(CD_TRANSACAO));
        LogUtils.adicionarContextoMDC(LogContextEnum.API_CONTEXT, req);

        log.info("Iniciando requisição");
    }

    private void printQueryParam(HttpServletRequest request) {
        if (request.getQueryString() != null) {
            log.info("======== lista de query ==========");
            String[] listaQuery = request.getQueryString().split("&");
            for (String query : listaQuery) {
                log.info(query);
            }
            log.info("=========================");
        }
        log.info("");
    }
}
