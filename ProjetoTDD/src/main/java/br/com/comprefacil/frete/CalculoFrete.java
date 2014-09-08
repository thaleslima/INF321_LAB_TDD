package br.com.comprefacil.frete;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.CResultado;
import org.tempuri.CServico;
import org.tempuri.CalcPrecoPrazoWSLocator;
import org.tempuri.CalcPrecoPrazoWSSoap;

public class CalculoFrete {
	public Frete calcularFrete(String peso, BigDecimal largura, BigDecimal comprimento, BigDecimal altura, String tipo, String cep)
	{
		Frete frete = new Frete();
		CalcPrecoPrazoWSSoap CorreioService;
		CResultado resultado;
		CServico servico;

		String valor = "";
		String prazo = "";
		String erroCod = "";
		String erroMsg = "";
		//CEPdeOrigem sempre será o CEP do IC 13081-970
		
		//tipo tem que ser uma String, para o UC-01 precisamos dos seguintes
		//	40010 - Sedex
		//  40215 - Sedex 10
		//	41106 - PAC
		
		//peso deve ser passado como String
		
		BigDecimal diametro = new BigDecimal(1);
		BigDecimal valorDeclarado = new BigDecimal(0);
		
		try {
			CorreioService = new CalcPrecoPrazoWSLocator().getCalcPrecoPrazoWSSoap();
			try {
				resultado = CorreioService.calcPrecoPrazo("", "", tipo, "13081970", cep, peso, 1, comprimento, altura, largura, diametro, "N", valorDeclarado, "N" );
				CServico[] service = resultado.getServicos();
				servico = (CServico) service[0];
				//Invoca WS do correio
				valor = servico.getValor();
				prazo = servico.getPrazoEntrega();
				erroCod = servico.getErro();
				erroMsg = servico.getMsgErro();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*if(peso <= 0)
		{
			frete.setStatus("peso inválido");
			return frete;
		}
		
		if(largura <= 0)
		{
			frete.setStatus("largura inválida");
			return frete;
		}
		
		if(comprimento <= 0)
		{
			frete.setStatus("comprimento inválida");
			return frete;
		}
		
		if(altura <= 0)
		{
			frete.setStatus("comprimento inválida");
			return frete;
		}
		
		
		if(tipo < 1 && tipo > 3)
		{
			frete.setStatus("tipo inválida");
			return frete;
		}
		
		if(frete.equals(""))
		{
			frete.setStatus("frete inválido");
			return frete;
		}*/
		

		valor = valor.replaceAll(",", ".");
		prazo = prazo.replaceAll(",", ".");
		
		frete.setValor(Double.parseDouble(valor));
		frete.setTempoEntrega(Double.parseDouble(prazo));
		frete.setErroCod(erroCod);
		frete.setErroMsg(erroMsg);
		
		return frete;
	}
}
