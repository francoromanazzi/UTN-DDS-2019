package UTN.QueMePongo;

import org.junit.Ignore;
import org.junit.Test;
import utils.MailSender;
import utils.SmsSender;

import javax.mail.MessagingException;

public class TestSenders {

	@Ignore
	@Test
	public void testMailSender() throws MessagingException {
		MailSender.send("ponerMail", "Prueba", "Envío el mail de prueba papa!");
	}

	@Ignore
	@Test
	public void testSmsSender() {
		String text = SmsSender.send("nro", "Envío el SMS de prueba papa!");
		System.out.println(text);
	}
}
