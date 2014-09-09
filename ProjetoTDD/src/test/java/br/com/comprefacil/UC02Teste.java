package br.com.comprefacil;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import br.comprefacil.endereco.Endereco;
import br.comprefacil.endereco.ValidacaoCEP;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class UC02Teste {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(18089);
	
	@Test
	public void endereco_CepValido() {
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

}
