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
		   //antes de chamar o STUB vamos validar a string CEP, ela deve estar no formato 11111-111
	    try{
	    	if(CEP.length() != 9){
	    		throw new Exception("CEP com tamanho invalido"); //a ideia aqui é retorna um endereço nulo, mas isso pode ser considerado um erro?
		        //como o teste ira verificar se está certo
		    } 
			else {
		      //verificar se CEP tem apenas numeros passados
		      CEP.replace("-","");
		    
		      if (CEP.matches("^[ a-zA-Z á]*$")){
		    	  throw new Exception("CEP não pode conter caracteres");
		      }
	
		   }
	   } catch(Exception ex){
		   System.out.println(ex.toString());
	   }
		
		
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
