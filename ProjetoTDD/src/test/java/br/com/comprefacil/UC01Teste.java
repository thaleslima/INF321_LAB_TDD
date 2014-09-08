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
	
	/*@Test
	public void calculoSedex_peso_testeValido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("30", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Integer valorDoErroCod = Integer.parseInt(frete.getErroCod());
		Assert.assertTrue(valorDoErroCod.equals(CodigoRetornoFrete.PESO_CORRETO.getValue()));
	}
	
	@Test
	public void calculoSedex10_pesoExcedido_testeValido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40215", "14400180");
		
		Integer valorDoErroCod = Integer.parseInt(frete.getErroCod());
		Assert.assertTrue(valorDoErroCod.equals(CodigoRetornoFrete.PESO_CORRETO.getValue()));
	}
	
	@Test
	public void calculoSedexPAC_pesoExcedido_testeValido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("28", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "41106", "14400180");
		
		Integer valorDoErroCod = Integer.parseInt(frete.getErroCod());
		Assert.assertTrue(valorDoErroCod.equals(CodigoRetornoFrete.PESO_CORRETO.getValue()));
	}*/

	@Test
	public void calculoSedex_pesoNegativo_testeInvalido() {
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("-1", new BigDecimal("10"), new BigDecimal("10"),
				new BigDecimal("10"), "40010", "14400180");

		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_NEGATIVO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void calculoSedex_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("31", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void calculoSedex10_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("16", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "40215", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void calculoSedexPAC_pesoExcedido_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("50", new BigDecimal("11"), new BigDecimal("16"),
				new BigDecimal("11"), "41106", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.PESO_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void calculo_comprimentoMinIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("9"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.COMPRIMENTO_MIN_INVALIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
	
	@Test
	public void calculo_comprimentoMaxIncorreto_testeInvalido(){
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("11"), new BigDecimal("106"),
				new BigDecimal("11"), "40010", "14400180");
		
		Assert.assertTrue(frete.getErroCod().equals(CodigoRetornoFrete.COMPRIMENTO_MAX_EXCEDIDO.getValue()));
		Assert.assertTrue(frete.getValor() == 0);
		Assert.assertTrue(frete.getTempoEntrega() == 0);
	}
}
