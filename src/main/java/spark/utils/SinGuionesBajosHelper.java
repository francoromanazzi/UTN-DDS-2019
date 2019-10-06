package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum SinGuionesBajosHelper implements Helper<Enum> {
	isTrue {
		@Override
		public CharSequence apply(Enum arg0, Options arg1) throws IOException {
			return arg0.toString().replaceAll("_", " ");
		}
	}
}
