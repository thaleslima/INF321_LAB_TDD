package br.com.comprefacil;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.comprefacil.frete.CalculoFrete;
import br.com.comprefacil.frete.CodigoRetornoFrete;
import br.com.comprefacil.frete.Frete;

public class UC01Teste {

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
}
