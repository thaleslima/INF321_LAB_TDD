package br.com.comprefacil;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import br.com.comprefacil.frete.CalculoFrete;
import br.com.comprefacil.frete.CodigoRetornoFrete;
import br.com.comprefacil.frete.Frete;

public class UC01Teste {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(18099);

	@Test
	public void valorDofrete_Valido() {
		//Este teste já contempla as dimensões como testes válidos
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getValor() > 0);
		Assert.assertTrue(frete.getTempoEntrega() > 0);
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_CORRETO.getValue()));
	}

	@Test
	public void Sedex_pesoNegativo_testeInvalido() {
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("-1", new BigDecimal("10"), new BigDecimal("10"),
				new BigDecimal("10"), "40010", "14400180");

		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_NEGATIVO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void Sedex_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("31", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void Sedex10_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("16", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40215", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void SedexPAC_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("50", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "41106", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void comprimentoMinIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("9"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.COMPRIMENTO_MIN_INVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void comprimentoMaxIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("106"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.COMPRIMENTO_MAX_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void tipoInvalido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("20"), new BigDecimal("20"), 
				new BigDecimal("20"), "11111", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.TIPOINVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega()==0);
	}
	
	@Test
	public void CEPInvalido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		
		//envio de menos de 8 caracteres
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "123456");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CEPDESTINVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega()==0);
		
		//envio de mais de 8 caracteres
		frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "123456789");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CEPDESTINVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega()==0);

		//envio de letra nos 8 caracteres
		frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "aabbbccc");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CEPDESTINVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega()==0);
	}
	
	@Test
	public void larguraMaxIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("106"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.LARGURA_MAX_EXCEDIDA.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void larguraMinIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("10"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.LARGURA_MIN_NEXCEDIDA.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void alturaMaxIncorreto_testeInvalido() {
		//Este teste já contempla as dimensões como testes válidos
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("106"), "40010", "14400180");
		
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.ALTURA_MAX_EXCEDIDA.getValue()));
	}
	
	@Test
	public void alturaMinIncorreto_testeInvalido() {
		//Este teste já contempla as dimensões como testes válidos
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("1"), "40010", "14400180");
		
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.ALTURA_MIN_EXCEDIDA.getValue()));
	}
	
	@Test
	public void somaDimensoesMaxIncorreto_testeInvalido() {
		//Este teste já contempla as dimensões como testes válidos
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("105"), new BigDecimal("105"),
				new BigDecimal("2"), "40010", "14400180");
		
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.SOMA_DIMENSOES_EXCEDIDA.getValue()));
	}
	
	@Test
	public void somaDimensoesLimite_testeValido() {
		//Este teste já contempla as dimensões como testes válidos
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("90"), new BigDecimal("90"),
				new BigDecimal("20"), "40010", "14400180");
		
		Assert.assertTrue(frete.getValor() > 0);
		Assert.assertTrue(frete.getTempoEntrega() > 0);
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.SOMA_CORRETA.getValue()));
	}
	
	@Test //verificar se a URL dos correios está correta
	public void URLCorreios_incorreta() {
		CalculoFrete calculoFrete = new CalculoFrete();
		calculoFrete.setURLCorreios("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx");
		
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("90"), new BigDecimal("90"),
				new BigDecimal("20"), "40010", "14400180");
	}
	
	@Test
	public void erroCorreiosForaAr_testeValido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"-33\","
						+ "\"erroMsg\":\"Sistema temporariamente fora do ar. Favor tentar mais tarde.\" }")));
	
		CalculoFrete calcular = new CalculoFrete();
		Frete frete = calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	
		Assert.assertTrue(new Double(0.0).equals(frete.getValor()));
		Assert.assertTrue(new Double(0.0).equals(frete.getTempoEntrega()));
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CORREIOS_FORA_DO_AR.getValue()));
		Assert.assertEquals("Sistema temporariamente fora do ar. Favor tentar mais tarde.",frete.getErroMsg());
				
	}
	
	@Test
	public void erroCalcularTarifa_testeValido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"-888\","
						+ "\"erroMsg\":\"Erro ao calcular a tarifa.\" }")));
	
		CalculoFrete calcular = new CalculoFrete();
		Frete frete = calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	
		Assert.assertTrue(new Double(0.0).equals(frete.getValor()));
		Assert.assertTrue(new Double(0.0).equals(frete.getTempoEntrega()));
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.ERRO_CALCULO_TARIFA.getValue()));
		Assert.assertEquals("Erro ao calcular a tarifa.",frete.getErroMsg());
				
	}
	
	@Test
	public void erroSistemaIndisponivel_testeValido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"7\","
						+ "\"erroMsg\":\"Servico indisponivel, tente mais tarde\" }")));
	
		CalculoFrete calcular = new CalculoFrete();
		Frete frete = calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	
		Assert.assertTrue(new Double(0.0).equals(frete.getValor()));
		Assert.assertTrue(new Double(0.0).equals(frete.getTempoEntrega()));
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CORREIOS_SERVICO_INDISPONIVEL.getValue()));
		Assert.assertEquals("Servico indisponivel, tente mais tarde",frete.getErroMsg());
				
	}
	
	@Test
	public void erroOutrosErros_testeValido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"99\","
						+ "\"erroMsg\":\"Outros erros diversos do .Net\" }")));
	
		CalculoFrete calcular = new CalculoFrete();
		Frete frete = calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	
		Assert.assertTrue(new Double(0.0).equals(frete.getValor()));
		Assert.assertTrue(new Double(0.0).equals(frete.getTempoEntrega()));
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CORREIOS_OUTROS_ERROS.getValue()));
		Assert.assertEquals("Outros erros diversos do .Net",frete.getErroMsg());
				
	}
	
	@Test (expected=ClientProtocolException.class)
	public void erroCloseConnectio_testeInvalido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"99\","
						+ "\"erroMsg\":\"Outros erros diversos do .Net\" }")
						.withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
	
		CalculoFrete calcular = new CalculoFrete();
		calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	}
	
	@Test (expected=NoHttpResponseException.class)
	public void erroEmptyResponse_testeInvalido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"99\","
						+ "\"erroMsg\":\"Outros erros diversos do .Net\" }")
						.withFault(Fault.EMPTY_RESPONSE)));
	
		CalculoFrete calcular = new CalculoFrete();
		calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	}
	
	public void delay_testeValido() throws ClientProtocolException, IOException, JSONException{
		String nCdEmpresa = "";
		String sDsSenha = "";
		String nCdServico = "40010";
		String sCepOrigem = "13081970";
		String sCepDestino = "14400180";
		String nVlPeso = "10";
		int nCdFormato = 1;
		BigDecimal nVlComprimento = new BigDecimal("90");
		BigDecimal nVlAltura = new BigDecimal("20");
		BigDecimal nVlLargura = new BigDecimal("90");
		BigDecimal nVlDiametro = new BigDecimal("1");
		String sCdMaoPropria = "N";
		BigDecimal nVlValorDeclarado = new BigDecimal("0");
		String sCdAvisoRecebimento = "N"; 
		
		stubFor(get(
				urlEqualTo("/getPrecoPrazo?nCdEmpresa=" + nCdEmpresa
						+ "&sDsSenha=" + sDsSenha 
						+ "&nCdServico=" + nCdServico
						+ "&sCepOrigem=" + sCepOrigem 
						+ "&sCepDestino=" + sCepDestino 
						+ "&nVlPeso=" + nVlPeso 
						+ "&nCdFormato="+ nCdFormato 
						+ "&nVlComprimento=" + nVlComprimento
						+ "&nVlAltura=" + nVlAltura 
						+ "&nVlLargura="+ nVlLargura 
						+ "&nVlDiametro=" + nVlDiametro
						+ "&sCdMaoPropria=" + sCdMaoPropria
						+ "&nVlValorDeclarado=" + nVlValorDeclarado
						+ "&sCdAvisoRecebimento=" + sCdAvisoRecebimento))
				.willReturn(
				aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"valor\":\"0\", \"tempoEntrega\":\"0\", \"erroCod\":\"99\","
						+ "\"erroMsg\":\"Outros erros diversos do .Net\" }")
						.withFixedDelay(300)));
	
		CalculoFrete calcular = new CalculoFrete();
		Frete frete = calcular.calcularFrete(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
	
		Assert.assertTrue(new Double(0.0).equals(frete.getValor()));
		Assert.assertTrue(new Double(0.0).equals(frete.getTempoEntrega()));
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.CORREIOS_OUTROS_ERROS.getValue()));
		Assert.assertEquals("Outros erros diversos do .Net",frete.getErroMsg());
	}
}
