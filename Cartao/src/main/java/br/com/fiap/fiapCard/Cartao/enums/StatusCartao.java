package br.com.fiap.fiapCard.Cartao.enums;

public enum StatusCartao {

    ATIVO(1),
    BLOQUEADO(2),
    EXPIRADO(3),
    INATIVO(4);

    private final int valor;

    StatusCartao(int status){
        valor = status;
    }

    public int getValor(){
        return valor;
    }

}
