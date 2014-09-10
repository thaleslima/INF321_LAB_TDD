package br.comprefacil.endereco;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.comprefacil.stub.StubCorreios;

public class ValidacaoCEP {

	public Endereco retornarEndereco(String CEP) throws ClientProtocolException, IOException, JSONException {
		//TODO fazer as validações, pode validar o tamanho do CEP, entre outras coisas
		
		Endereco endereco = new Endereco();
		//chama o stub
		StubCorreios correios = new StubCorreios();
		JSONObject parseEndereco;
		
		//TODO validar exceções
		parseEndereco = correios.getEndereco(CEP);
		endereco.setNomeBairro(parseEndereco.getString("bairro"));
		endereco.setNomeLocalizacao(parseEndereco.getString("localizacao"));
		endereco.setNomeLogradouro(parseEndereco.getString("logradouro"));
		endereco.setNumeroLogradouro(parseEndereco.getInt("numero"));
		endereco.setSiglaUF(parseEndereco.getString("uf"));
		endereco.setTipoLogradouro(parseEndereco.getString("tipo"));

		return endereco;
	}
}
