package br.com.comprefacil.frete;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.comprefacil.stub.StubCorreios;

public class CalculoFreteStub {

	public Frete calcularFreteStub(String nCdEmpresa, String sDsSenha, String nCdServico, String sCepOrigem, String sCepDestino, String nVlPeso, int nCdFormato, java.math.BigDecimal nVlComprimento, java.math.BigDecimal nVlAltura, java.math.BigDecimal nVlLargura, java.math.BigDecimal nVlDiametro, String sCdMaoPropria, java.math.BigDecimal nVlValorDeclarado, String sCdAvisoRecebimento)
	{
		Frete frete = new Frete();
		StubCorreios correios = new StubCorreios();
		JSONObject parseFrete;


		//TODO validar exceções
		try {
			parseFrete = correios.getPrecoPrazoResponse(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
			frete.setValor(parseFrete.getDouble("valor"));
			frete.setTempoEntrega(parseFrete.getDouble("tempoEntrega"));
			frete.setErroCod(parseFrete.getInt("erroCod"));
			frete.setErroMsg(parseFrete.getString("erroMsg"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return frete;
	
}
}
