/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wservice;

//import wservice.BaseWebService;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe para abstrair as chamadas ao WebService ByJG para SMS
 *
 * @author jg
 */
public class SMSService extends BaseWebService {

	protected static final String SERVICE = "sms";

	protected String usuario = "";
	protected String senha = "";

	public SMSService(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}

	/**
	 * Retorna a versao do WebService
	 *
	 * @return Versão Retorna a versão do serviço
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String obterVersao() throws Exception {
		return this.executeWebService(SMSService.SERVICE, "obterVersao", null);
	}

	/**
	 * Enviar um SMS
	 *
	 * @param ddd DDD do telefone
	 * @param celular Número do telefone
	 * @param mensagem Mensagem a ser enviada
	 * @return Status do Envio
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String enviarSMS(String ddd, String celular, String mensagem) throws Exception {
		return this.enviarSMS(ddd, celular, mensagem, null);
	}

	/**
	 * Enviar SMS com o SenderID (requer cadastro)
	 *
	 * @param ddd DDD do telefone
	 * @param celular Número do telefone
	 * @param mensagem Mensagem a ser enviada
	 * @param senderid SenderID
	 * @return  Status do Envio
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String enviarSMS(String ddd, String celular, String mensagem, String senderid) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("ddd", ddd);
		params.put("celular", celular);
		params.put("mensagem", mensagem);
		params.put("usuario", this.usuario);
		params.put("senha", this.senha);
		if (senderid != null) {
			params.put("senderid", senderid);
		}

		return this.executeWebService(SMSService.SERVICE, "enviarsms2", params);
	}

	/**
	 * Enviar uma lista de SMS para serem enviados por vez (máximo 50).
	 *
	 * @param lista Lista de telefones no formato DDPPPPNNNN
	 * @param mensagem Mensagem a ser enviada
	 * @return Status do Envio
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String enviarListaSMS(ArrayList lista, String mensagem) throws Exception {
		return this.enviarListaSMS(lista, mensagem, null);
	}

	/**
	 * Enviar uma lista de SMS para serem enviados por vez (máximo 50) com
	 * SenderID (requer cadastro).
	 *
	 * @param lista Lista de telefones no formato DDPPPPNNNN
	 * @param mensagem Mensagem a ser enviada
	 * @param senderid SenderID
	 * @return Status do Envio
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String enviarListaSMS(ArrayList lista, String mensagem, String senderid) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		String strLista = "";
		for (Object telefone : lista) {
			strLista += (strLista.length() > 0 ? "|" : "") + telefone.toString();
		}
		params.put("lista", strLista);
		params.put("mensagem", mensagem);
		params.put("usuario", this.usuario);
		params.put("senha", this.senha);
		if (senderid != null) {
			params.put("senderid", senderid);
		}

		return this.executeWebService(SMSService.SERVICE, "enviarlistasms", params);
	}

	/**
	 * Consulta Créditos
	 *
	 * @return Quantidade de Créditos
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String creditos() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("usuario", this.usuario);
		params.put("senha", this.senha);

		return this.executeWebService(SMSService.SERVICE, "creditos", params);
	}
}
