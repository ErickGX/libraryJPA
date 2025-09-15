package com.erickgx.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException {

    @Getter
    private String campo;


    //Exception que exibe campos afetados , para a regra de negocio
        public CampoInvalidoException(String campo, String mensagem){
                super(mensagem);
                this.campo = campo;
        }

}
