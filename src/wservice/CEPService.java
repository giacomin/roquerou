/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wservice;

//import wservice.BaseWebService;
import java.util.HashMap;

/**
 * Classe para abstrair as chamadas ao WebService ByJG para SMS
 *
 * @author jg
 */
public class CEPService extends BaseWebService {

	protected static final String SERVICE = "cep";

	protected String usuario = "";
	protected String senha = "";

	public CEPService(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}

	/**
	 * Retorna a versao do WebService
	 *
	 * @return Versão Versão do componente
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String obterVersao() throws Exception {
		return this.executeWebService(CEPService.SERVICE, "obterVersao", null);
	}

	/**
	 * Obtém o logradouro
	 *
	 * @param cep CEP no formato "NNNNNPPP" ou "NNNNNN-PPP"
	 * @return Retorna o logradouro
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String obterLogradouro(String cep) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);
		params.put("usuario", this.usuario);
		params.put("senha", this.senha);

		return this.executeWebService(CEPService.SERVICE, "obterLogradouroAuth", params);
	}

	/**
	 * Retorna o CEP à partir do logradouro.
	 * @param logradouro Logradouro (sem rua, avenida ou número)
	 * @param localidade Nome da localidade
	 * @param UF Estado da localidade
	 * @return Retorna o CEP
	 * @throws Exception Dispara se não conseguir acessar o serviço
	 */
	public String obterCEP(String logradouro, String localidade, String UF) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("logradouro", logradouro);
		params.put("localidade", localidade);
		params.put("UF", UF);
		params.put("usuario", this.usuario);
		params.put("senha", this.senha);

		return this.executeWebService(CEPService.SERVICE, "obterCEPAuth", params);
	}
}
