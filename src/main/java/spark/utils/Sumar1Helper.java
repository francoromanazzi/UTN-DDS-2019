package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum Sumar1Helper implements Helper<Integer> {
	isTrue {
		@Override
		public CharSequence apply(Integer arg0, Options arg1) throws IOException {
			return Integer.toString(arg0 + 1);
		}
	}
}