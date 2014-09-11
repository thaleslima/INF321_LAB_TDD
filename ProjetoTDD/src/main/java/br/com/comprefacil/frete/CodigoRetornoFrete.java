package br.com.comprefacil.frete;

public enum CodigoRetornoFrete {
	
	PESO_NEGATIVO(-20),PESO_EXCEDIDO(-4), PESO_CORRETO(0), 
	COMPRIMENTO_MIN_INVALIDO(-22), COMPRIMENTO_MAX_EXCEDIDO(-15),
	TIPOINVALIDO(-1), CEPDESTINVALIDO(-3),
	LARGURA_MIN_NEXCEDIDA(-20), LARGURA_MAX_EXCEDIDA(-16),
	ALTURA_MAX_EXCEDIDA(-17),ALTURA_MIN_EXCEDIDA(-18),
	SOMA_DIMENSOES_EXCEDIDA(-23),SOMA_CORRETA(0), 
	CORREIOS_FORA_DO_AR(-33),ERRO_CALCULO_TARIFA(-888),
	CORREIOS_SERVICO_INDISPONIVEL(7),CORREIOS_OUTROS_ERROS(99);
	
	private int value;

    private CodigoRetornoFrete(int value) {
            this.value = value;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
        
}
