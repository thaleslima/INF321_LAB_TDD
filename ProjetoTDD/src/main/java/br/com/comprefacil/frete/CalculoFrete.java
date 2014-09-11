package br.com.comprefacil.frete;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.tempuri.CResultado;
import org.tempuri.CServico;
import org.tempuri.CalcPrecoPrazoWSLocator;
import org.tempuri.CalcPrecoPrazoWSSoap;

import br.com.comprefacil.stub.StubCorreios;

public class CalculoFrete {

	public Frete calcularFrete(String peso, BigDecimal largura,
			BigDecimal comprimento, BigDecimal altura, String tipo, String cep) {
		Frete frete = new Frete();
		CalcPrecoPrazoWSSoap CorreioService;
		CResultado resultado;
		CServico servico;

		String valor = "";
		String prazo = "";
		String erroCod = "";
		String erroMsg = "";
		// CEPdeOrigem sempre ser� o CEP do IC 13081-970

		// tipo tem que ser uma String, para o UC-01 precisamos dos seguintes
		// 40010 - Sedex
		// 40215 - Sedex 10
		// 41106 - PAC

		// peso deve ser passado como String

		BigDecimal diametro = new BigDecimal(1);
		BigDecimal valorDeclarado = new BigDecimal(0);

		try {
			CorreioService = new CalcPrecoPrazoWSLocator()
					.getCalcPrecoPrazoWSSoap();
			try {
				resultado = CorreioService.calcPrecoPrazo("", "", tipo,
						"13081970", cep, peso, 1, comprimento, altura, largura,
						diametro, "N", valorDeclarado, "N");
				CServico[] service = resultado.getServicos();
				servico = (CServico) service[0];
				// Invoca WS do correio
				valor = servico.getValor();
				prazo = servico.getPrazoEntrega();
				erroCod = servico.getErro();
				erroMsg = servico.getMsgErro();
			} catch (RemoteException e) {
				// TODO Auto-generated catch blockxxxx
				e.printStackTrace();
			}
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * if(peso <= 0) { frete.setStatus("peso inv�lido"); return frete; }
		 * 
		 * if(largura <= 0) { frete.setStatus("largura inv�lida"); return frete;
		 * }
		 * 
		 * if(comprimento <= 0) { frete.setStatus("comprimento inv�lida");
		 * return frete; }
		 * 
		 * if(altura <= 0) { frete.setStatus("comprimento inv�lida"); return
		 * frete; }
		 * 
		 * 
		 * if(tipo < 1 && tipo > 3) { frete.setStatus("tipo inv�lida"); return
		 * frete; }
		 * 
		 * if(frete.equals("")) { frete.setStatus("frete inv�lido"); return
		 * frete; }
		 */

		valor = valor.replaceAll(",", ".");
		prazo = prazo.replaceAll(",", ".");

		frete.setValor(valor == null ? 0.0 : Double.parseDouble(valor));
		frete.setTempoEntrega(prazo == null ? 0.0 :Double.parseDouble(prazo));
		frete.setErroCod(erroCod == null ? 0 : Integer.parseInt(erroCod));
		frete.setErroMsg(erroMsg == null ? "" : erroMsg);

		return frete;
	}

	public Frete calcularFrete(String nCdEmpresa, String sDsSenha, String nCdServico, String sCepOrigem, String sCepDestino, String nVlPeso, int nCdFormato, java.math.BigDecimal nVlComprimento, java.math.BigDecimal nVlAltura, java.math.BigDecimal nVlLargura, java.math.BigDecimal nVlDiametro, String sCdMaoPropria, java.math.BigDecimal nVlValorDeclarado, String sCdAvisoRecebimento) throws ClientProtocolException, IOException, JSONException{
		Frete frete = new Frete();
		
		//chama o stub
		StubCorreios correios = new StubCorreios();
		JSONObject parseCorreio;
	
		parseCorreio = correios.getPrecoPrazoResponse(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
		frete.setValor(parseCorreio.getInt("valor"));
		frete.setTempoEntrega(parseCorreio.getDouble("tempoEntrega"));
		frete.setErroMsg(parseCorreio.getString("erroMsg"));
		frete.setErroCod(parseCorreio.getInt("erroCod"));
		
		return frete;
	}
}
