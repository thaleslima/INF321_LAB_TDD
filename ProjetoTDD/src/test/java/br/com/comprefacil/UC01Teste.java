package br.com.comprefacil;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.comprefacil.frete.CalculoFrete;
import br.com.comprefacil.frete.Frete;

public class UC01Teste {

	@Test
	public void valorDofrete_Valido() {
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("10", new BigDecimal("10"), new BigDecimal("10"),
				new BigDecimal("10"), "40010", "14400180");

		Assert.assertTrue(frete.getValor() > 0);
		Assert.assertTrue(frete.getTempoEntrega() > 0);
		Assert.assertTrue(frete.getErroCod().equals("0"));
	}

	@Test
	public void Peso_Invalido() {
		CalculoFrete calculoFrete = new CalculoFrete();
		Frete frete = calculoFrete.calcularFrete("-1", new BigDecimal("10"), new BigDecimal("10"),
				new BigDecimal("10"), "40010", "14400180");

		Assert.assertTrue(frete.getErroCod().equals("peso inválido"));
	}
}
