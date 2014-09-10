package br.com.comprefacil.stub;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
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
	
	 class HttpFetcher {
		 public String fetchAsString(String url) throws ClientProtocolException, IOException {
			 return Request.Get(url).execute().returnContent().asString();
		 }
	 }
}
