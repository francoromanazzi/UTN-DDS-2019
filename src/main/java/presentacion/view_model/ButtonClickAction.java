package presentacion.view_model;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.uqbar.lacar.ui.model.Action;

public class ButtonClickAction implements Action{
	private EventosViewModel viewModel;
	
	public ButtonClickAction(EventosViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	@Override
	public void execute() {
		viewModel.setFechaInicio(LocalDateTime.of(viewModel.getAnioInicio(), viewModel.getMesInicio(), viewModel.getDiaInicio(), 0, 0));
		viewModel.setFechaFin(LocalDateTime.of(viewModel.getAnioFin(), viewModel.getMesFin(), viewModel.getDiaFin(), 0, 0));
		viewModel.setEventosFiltrados(viewModel.getEventos().stream().filter(eo -> eo.getEvento().getFechaInicio().isAfter(viewModel.getFechaInicio()) &&
				eo.getEvento().getFechaInicio().isBefore(viewModel.getFechaFin())).collect(Collectors.toList()));
	}

}
