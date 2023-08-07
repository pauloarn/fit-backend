package com.bolo.fit.enums;

public enum LogContextEnum {
    RABBIT_CONTEXT("rabbitMQ"),
    API_CONTEXT("api");


    private String descricao;

    LogContextEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getDescricao(String arg) {
        return this.descricao + " :: " + arg;
    }
}
