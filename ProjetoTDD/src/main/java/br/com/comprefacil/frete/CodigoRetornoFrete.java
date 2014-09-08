package br.com.comprefacil.frete;

public enum CodigoRetornoFrete {
	
	PESO_NEGATIVO(-20),PESO_EXCEDIDO(-4), PESO_CORRETO(0), COMPRIMENTO_MIN_INVALIDO(-22),
	COMPRIMENTO_MAX_EXCEDIDO(-15);
	
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
