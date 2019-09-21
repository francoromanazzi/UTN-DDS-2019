package modelo.evento;

public enum FrecuenciaEvento {
	//Periodo en milisegundos
	UNICA_VEZ {
		@Override
		public long getPerido() {
			return 0L;
		}
	}, DIARIO {
		@Override
		public long getPerido() {
			return (60L * 60L * 24L) * 1000L;
		}
	}, SEMANAL {
		@Override
		public long getPerido() {
			return (60L * 60L * 24L * 7L) * 1000L;
		}
	}, MENSUAL {
		@Override
		public long getPerido() {
			return (60L * 60L * 24L * 30L) * 1000L;
		}
	}, ANUAL {
		@Override
		public long getPerido() {
			return (60L * 60L * 24L * 365L) * 1000L;
		}
	};

	abstract public long getPerido();
}
