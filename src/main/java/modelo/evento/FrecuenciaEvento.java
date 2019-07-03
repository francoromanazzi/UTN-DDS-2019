package modelo.evento;

public enum FrecuenciaEvento {
	UNICA_VEZ {
		@Override
		public long getPerido() {
			return 0;
		}
	}, DIARIO {
		@Override
		public long getPerido() {
			return 60L * 60L * 24L;
		}
	}, SEMANAL {
		@Override
		public long getPerido() {
			return 60L * 60L * 24L * 7L;
		}
	}, MENSUAL {
		@Override
		public long getPerido() {
			return 60L * 60L * 24L * 30L;
		}
	}, ANUAL {
		@Override
		public long getPerido() {
			return 60L * 60L * 24L * 365L;
		}
	};

	abstract public long getPerido();
}
