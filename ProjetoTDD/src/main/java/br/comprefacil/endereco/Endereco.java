package br.comprefacil.endereco;

public class Endereco {
	private String tipoLogradouro;
	private String nomeLogradouro;
	private int numeroLogradouro;
	private String nomeBairro;
	private String nomeLocalizacao;
	private String siglaUF;
	private String CEP;
	
	public String getTipoLogradouro() {
		return tipoLogradouro;
	}
	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}
	public int getNumeroLogradouro() {
		return numeroLogradouro;
	}
	public void setNumeroLogradouro(int numeroLogradouro) {
		this.numeroLogradouro = numeroLogradouro;
	}
	public String getNomeBairro() {
		return nomeBairro;
	}
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}
	public String getNomeLocalizacao() {
		return nomeLocalizacao;
	}
	public void setNomeLocalizacao(String nomeLocalizacao) {
		this.nomeLocalizacao = nomeLocalizacao;
	}
	public String getSiglaUF() {
		return siglaUF;
	}
	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
}
