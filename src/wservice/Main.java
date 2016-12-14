/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wservice;



/**
 *
 * @author jg
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		try {
			SMSService sms = new SMSService("eduardopaa", "eduardo");
			System.out.println(sms.obterVersao());
			//System.out.println(sms.enviarSMS("51", "992150368", "Mensagem de teste"));
			System.out.println(sms.creditos());

			CEPService cep = new CEPService("eduardopaa", "eduardo");
			System.out.println(cep.obterVersao());
			System.out.println(cep.obterLogradouro("95900971"));
			System.out.println(cep.obterCEP("logradouro", "rio de janeiro", "rj"));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
