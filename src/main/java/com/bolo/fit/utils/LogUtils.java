package com.bolo.fit.utils;

import com.bolo.fit.enums.LogContextEnum;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import java.util.UUID;

@Log4j2
public class LogUtils {

    private static final String LOG_CD_TRANSACAO = "cdTransacao";
    private static final String LOG_CONTEXTO = "contexto";

    public static String gerarIdentificacaoUnica() {
        return UUID.randomUUID().toString();
    }

    public static void adicionarCdTransacao(String cdTransacao) {
        try {
            String codigoTransacao = cdTransacao != null ? cdTransacao : gerarIdentificacaoUnica();
            MDC.put(LogUtils.LOG_CD_TRANSACAO, String.format("[%s]", codigoTransacao));
        } catch (Exception e) {
            log.warn("Erro ao adicionar código de transação", e);
        }
    }

    public static void adicionarContextoMDC(LogContextEnum contexto, String arg) {
        try {
            MDC.put(LogUtils.LOG_CONTEXTO, String.format("[%s]", contexto.getDescricao(arg)));
        } catch (Exception e) {
            log.warn("Erro ao adicionar contexto ao MDC", e);
        }
    }

    public static void limparContextoMDC(LogContextEnum logContext) {
        try {
            if (existeContexto(logContext)) {
                MDC.remove(LOG_CONTEXTO);
            }
        } catch (Exception e) {
            log.warn(" Erro ao limpar o MDC", e);
        }
    }

    public static boolean existeContexto(LogContextEnum logContext) {
        try {
            String log = MDC.get(LogUtils.LOG_CONTEXTO);
            if (log != null) {
                return log.matches(LogUtils.buildContextoRegex(logContext));
            }
        } catch (Exception e) {
            log.warn(" Erro ao recuperar código de transação do MDC", e);
        }
        return false;
    }

    private static String buildContextoRegex(LogContextEnum logContext) {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(logContext.getDescricao());
        builder.append(".*");
        builder.append("]");

        return builder.toString();
    }
}
