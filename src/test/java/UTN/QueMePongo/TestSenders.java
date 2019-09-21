package UTN.QueMePongo;

import excepciones.MensajeriaException;
import org.junit.Ignore;
import org.junit.Test;
import utils.MailSender;
import utils.SmsSender;

import javax.mail.MessagingException;

public class TestSenders {

	@Test(expected = MensajeriaException.class)
	public void deberiaFallarSiElMailEsErroneo() {
		MailSender.send("asd", "Prueba", "Envío el mail de prueba papa!");
	}

	@Test
	public void deberiaPoderMandarUnMail() {
		MailSender.send("utnquemepongo@gmail.com", "Prueba", "Envío el mail de prueba papa!");
	}

	@Ignore
	@Test
	public void testMailSender() {
		MailSender.send("ponerMail", "Prueba", "Envío el mail de prueba papa!");
	}

	@Ignore
	@Test
	public void testSmsSender() {
		String text = SmsSender.send("nro", "Envío el SMS de prueba papa!");
		System.out.println(text);
	}
}
