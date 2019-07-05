package UTN.QueMePongo;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.junit.Ignore;
import org.junit.Test;
import utils.MailSender;
import utils.SmsSender;

public class TestSenders {

	@Ignore
	@Test
	public void testMailSender() throws AddressException, MessagingException {
		MailSender.send("ponerMail", "Prueba", "Envío el mail de prueba papa!");
	}
	
	@Ignore
	@Test
	public void testSmsSender() {
		String text = SmsSender.send("nro", "Envío el SMS de prueba papa!");
		System.out.println(text);
	}
}
