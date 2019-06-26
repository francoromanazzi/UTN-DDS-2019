package UTN.QueMePongo;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.junit.Test;
import utils.MailSender;

public class TestMailSender {
	
	@Test
	public void test() throws AddressException, MessagingException {
		MailSender.send("ponerMailParaProbarRecibirElMailOK", "Prueba", "Envío el mail de prueba papa!");
	}
}
