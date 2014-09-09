package br.com.comprefacil.frete;

public enum CodigoRetornoFrete {
	
	PESO_NEGATIVO(-20),PESO_EXCEDIDO(-4), PESO_CORRETO(0), 
	COMPRIMENTO_MIN_INVALIDO(-22), COMPRIMENTO_MAX_EXCEDIDO(-15),
	TIPOINVALIDO(-1), CEPDESTINVALIDO(-3),
	LARGURA_MIN_NEXCEDIDA(-20), LARGURA_MAX_EXCEDIDA(-16);
	
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
