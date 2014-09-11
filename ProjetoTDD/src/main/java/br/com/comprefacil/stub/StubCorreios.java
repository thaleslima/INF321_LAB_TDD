package br.com.comprefacil.stub;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;


public class StubCorreios {
	
    private HttpFetcher instance = new HttpFetcher();

	public JSONObject getEndereco(String CEP) throws ClientProtocolException, IOException, JSONException {
		//retorna um JSON ao invés de um html
		JSONObject parseObject = null;
		String endereco = instance.fetchAsString("http://localhost:18089/buscaEndereco?cep=" + CEP);
		
		parseObject = new JSONObject(endereco);
		
		//return JSONObject porque o tratamento dos dados será realizado no endereço, essa classe servindo 
		//apenas como stub.
		return parseObject;
	}
	 
	public JSONObject getPrecoPrazoResponse(String nCdEmpresa, String sDsSenha, String nCdServico, String sCepOrigem, String sCepDestino, String nVlPeso, int nCdFormato, java.math.BigDecimal nVlComprimento, java.math.BigDecimal nVlAltura, java.math.BigDecimal nVlLargura, java.math.BigDecimal nVlDiametro, String sCdMaoPropria, java.math.BigDecimal nVlValorDeclarado, String sCdAvisoRecebimento) throws ClientProtocolException, IOException, JSONException {
		JSONObject parseObject = null;
		String response = instance
				.fetchAsString("http://localhost:18099/getPrecoPrazo?nCdEmpresa="
						+ nCdEmpresa
						+ "&sDsSenha="	+ sDsSenha
						+ "&nCdServico="+ nCdServico
						+ "&sCepOrigem="+ sCepOrigem
						+ "&sCepDestino="+ sCepDestino
						+ "&nVlPeso="+ nVlPeso
						+ "&nCdFormato="+ nCdFormato
						+ "&nVlComprimento="+ nVlComprimento
						+ "&nVlAltura="	+ nVlAltura
						+ "&nVlLargura="+ nVlLargura
						+ "&nVlDiametro="+ nVlDiametro
						+ "&sCdMaoPropria="	+ sCdMaoPropria
						+ "&nVlValorDeclarado="	+ nVlValorDeclarado
						+ "&sCdAvisoRecebimento="+ sCdAvisoRecebimento);
		parseObject = new JSONObject(response);
		return parseObject;

	}
}
