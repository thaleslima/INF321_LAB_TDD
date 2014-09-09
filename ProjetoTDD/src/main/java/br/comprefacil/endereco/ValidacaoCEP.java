package br.comprefacil.endereco;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.comprefacil.stub.StubCorreios;

public class ValidacaoCEP {

	public Endereco retornarEndereco(String CEP) {
		//TODO fazer as validações
		
		Endereco endereco = new Endereco();
		//chama o stub
		StubCorreios correios = new StubCorreios();
		JSONObject parseEndereco;
		
		//TODO validar exceções
		try {
			parseEndereco = correios.getEndereco(CEP);
			endereco.setNomeBairro(parseEndereco.getString("bairro"));
			endereco.setNomeLocalizacao(parseEndereco.getString("localizacao"));
			endereco.setNomeLogradouro(parseEndereco.getString("logradouro"));
			endereco.setNumeroLogradouro(parseEndereco.getInt("numero"));
			endereco.setSiglaUF(parseEndereco.getString("uf"));
			endereco.setTipoLogradouro(parseEndereco.getString("tipo"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return endereco;
	}
}
