package br.com.alura.carteira.dto;

import java.math.BigDecimal;

public interface ItemCarteiraProjection {

    String getTicker();

    Long getQuantidade();

    BigDecimal getPercentual();

}


