package br.com.comprefacil.frete;

public class Frete {
	private double valor;
	private double tempoEntrega;
	private Integer erroCod;
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
	public Integer getErroCod() {
		return erroCod;
	}
	public void setErroCod(Integer erroCod) {
		this.erroCod = erroCod;
	}
	public String getErroMsg() {
		return erroMsg;
	}
	public void setErroMsg(String erroMsg) {
		this.erroMsg = erroMsg;
	}
	
}
