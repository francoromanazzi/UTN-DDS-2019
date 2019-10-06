package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.util.List;

public enum SizeHelper implements Helper<List> {
	isTrue {
		@Override
		public CharSequence apply(List arg0, Options arg1) throws IOException {
			return Integer.toString(arg0.size());
		}
	}
}
