package br.com.comprefacil.frete;

public class Frete {
	private double valor;
	private double tempoEntrega;
	private String erroCod;
	private String erroMsg;
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getTempoEntrega() {
		return tempoEntrega;
	}
	public void setTempoEntrega(double tempoEntrega) {
		this.tempoEntrega = tempoEntrega;
	}
	public String getErroCod() {
		return erroCod;
	}
	public void setErroCod(String erroCod) {
		this.erroCod = erroCod;
	}
	public String getErroMsg() {
		return erroMsg;
	}
	public void setErroMsg(String erroMsg) {
		this.erroMsg = erroMsg;
	}
	
}
