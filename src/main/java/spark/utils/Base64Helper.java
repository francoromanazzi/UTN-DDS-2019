package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import modelo.prenda.Imagen;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public enum Base64Helper implements Helper<Optional<Imagen>> {
	isTrue {
		@Override
		public CharSequence apply(Optional<Imagen> arg0, Options arg1) throws IOException {
			BufferedImage bufferedImage = arg0.get().getImagen();

			String imageString = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ImageIO.write(bufferedImage, "jpg", bos);
			byte[] imageBytes = bos.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			bos.close();

			return imageString;
		}
	}
}