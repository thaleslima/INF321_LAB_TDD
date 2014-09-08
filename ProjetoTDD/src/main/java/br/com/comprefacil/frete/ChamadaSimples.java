package br.com.comprefacil.frete;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.CResultado;
import org.tempuri.CServico;
import org.tempuri.CalcPrecoPrazoWSLocator;
import org.tempuri.CalcPrecoPrazoWSSoap;

public class ChamadaSimples {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				resultado = CorreioService.calcPrecoPrazo("", "", "40010", "13081970", "13333516", 
						"1", 1, new BigDecimal("30"), new BigDecimal("30"), new BigDecimal("30"), diametro, "N", valorDeclarado, "N" );
				CServico[] service = resultado.getServicos();
				servico = (CServico) service[0];
				//Invoca WS do correio
				valor = servico.getValor();
				prazo = servico.getPrazoEntrega();
				erroCod = servico.getErro();
				erroMsg = servico.getMsgErro();
				System.out.println("Valor: R$ " + valor + "\nPrazo: " + prazo + " dias");
				System.out.println("Cod. Erro: " + erroCod + "\nMensagem: " + erroMsg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
