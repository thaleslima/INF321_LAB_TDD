package br.com.comprefacil;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;

import org.apache.http.MalformedChunkCodingException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import br.comprefacil.endereco.Endereco;
import br.comprefacil.endereco.ValidacaoCEP;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class UC02Teste {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(18089);
	
	@Test
	public void endereco_CepValido() throws ClientProtocolException, IOException, JSONException {
		String CEP = "13171-130";
		stubFor(get(urlEqualTo("/buscaEndereco?cep=" + CEP)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{ \"tipo\":\"Rua\", \"logradouro\":\"Bahia\", \"numero\":\"452\","
								+ "\"bairro\":\"Nova Veneza\",\"localizacao\":\"Sumare\",\"uf\":\"SP\" }")));
		
		ValidacaoCEP validacao = new ValidacaoCEP();
		Endereco endereco = validacao.retornarEndereco("13171-130");
		
		Assert.assertNotNull(endereco);
		Assert.assertEquals("Rua", endereco.getTipoLogradouro());
		Assert.assertEquals("Bahia", endereco.getNomeLogradouro());
		Assert.assertEquals(452, endereco.getNumeroLogradouro());
		Assert.assertEquals("Nova Veneza", endereco.getNomeBairro());
		Assert.assertEquals("Sumare", endereco.getNomeLocalizacao());
		Assert.assertEquals("SP", endereco.getSiglaUF());
	}
	
	@Test (expected=JSONException.class)
	public void endereco_exceptionJson_testeInvalido() throws ClientProtocolException, IOException, JSONException {
		//retira de um elemento, quando for pegar os valores no ValidacaoCEP retornará uma exceção.
		String CEP = "13171-130";
		stubFor(get(urlEqualTo("/buscaEndereco?cep=" + CEP)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{ \"tipo\":\"Rua\", \"logradouro\":\"Bahia\", \"numero\":\"452\","
								+ "\"bairro\":\"Nova Veneza\",\"localizacao\":\"Sumare\" }")));
		
		ValidacaoCEP validacao = new ValidacaoCEP();
		validacao.retornarEndereco("13171-130");
	}
	
	@Test (expected=MalformedChunkCodingException.class)
	public void endereco_exceptionHttp_testeInvalido() throws ClientProtocolException, IOException, JSONException {
		//retira o "/" do "/buscaEndereco?cep=" irá ocasionar erro ao localizar a página
		String CEP = "13171-130";
		stubFor(get(urlEqualTo("/buscaEndereco?cep=" + CEP)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{ \"tipo\":\"Rua\", \"logradouro\":\"Bahia\", \"numero\":\"452\","
								+ "\"bairro\":\"Nova Veneza\",\"localizacao\":\"Sumare\",\"uf\":\"SP\"  }")
								.withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
		
		ValidacaoCEP validacao = new ValidacaoCEP();
		validacao.retornarEndereco("13171-130");
	}
	
}
