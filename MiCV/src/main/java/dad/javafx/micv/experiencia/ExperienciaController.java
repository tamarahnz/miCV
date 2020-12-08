package dad.javafx.micv.experiencia;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.dialogos.DialogoExperiencia;
import dad.javafx.micv.App;
//import dad.javafx.micv.model.ExpLaboral;
import dad.javafx.micv.model.Experiencia;
import dad.javafx.micv.model.Titulo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable {

	//model
		private ObjectProperty<Experiencia> experiencia = new SimpleObjectProperty<>();
		private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
		//private ObjectProperty<ExpLaboral> explaboral = new SimpleObjectProperty<>();
		
	//view
	@FXML
	private BorderPane view;

	@FXML
	private TableView<Experiencia> experienciaTV;

	@FXML
	private TableColumn<Experiencia, LocalDate> desdeTC;

	@FXML
	private TableColumn<Experiencia, LocalDate> hastaTC;

	@FXML
	private TableColumn<Experiencia, String> denominacionTC;

	@FXML
	private TableColumn<Experiencia, String> empleadorTC;

	@FXML
	private Button añadirBt;

	@FXML
	private Button eliminarBt;

	//TODO añadir
	@FXML
	void onActionAñadir(ActionEvent event) {
		DialogoExperiencia dialogo = new DialogoExperiencia();
		Optional<Experiencia> resultado = dialogo.showAndWait();
		
		if (resultado.isPresent()) {
			experiencias.get().add(resultado.get());
		}
	}

	@FXML
	void onActionEliminar(ActionEvent event) {
		String titulo = "Eliminar experiencia";
		String cabecera = "¿Desea eliminar la experiencia?";
		Experiencia exp = experiencia.get();
			
		if (exp != null && App.confirm(titulo, cabecera)) {
			experiencias.get().remove(exp);
		}
	}

	
	public ExperienciaController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
	}
		
	public void initialize(URL location, ResourceBundle resources) {
		desdeTC.setCellValueFactory(v -> v.getValue().desdeProperty());
		desdeTC.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaTC.setCellValueFactory(v -> v.getValue().hastaProperty());
		hastaTC.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionTC.setCellValueFactory(v -> v.getValue().denominacionProperty());
		denominacionTC.setCellFactory(TextFieldTableCell.forTableColumn());
		empleadorTC.setCellValueFactory(v -> v.getValue().empleadorProperty());
		empleadorTC.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.experiencias.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
			
	}
	
	/*private void onExpLaboralChanged(ObservableValue<? extends ExpLaboral> o, ExpLaboral ov, ExpLaboral nv) {

		if (ov != null) {
			desdeTC.textProperty().unbindBidirectional(ov.desdeProperty());
			hastaTC.textProperty().unbindBidirectional(ov.hastaProperty());
			denominacionTC.textProperty().unbindBidirectional(ov.denominacionProperty());
			empleadorTC.textProperty().unbindBidirectional(ov.empleadorProperty());
		}

		if (nv != null) {
			desdeTC.textProperty().bindBidirectional(nv.desdeProperty());
			hastaTC.textProperty().bindBidirectional(nv.hastaProperty());
			denominacionTC.textProperty().bindBidirectional(nv.denominacionProperty());
			empleadorTC.textProperty().bindBidirectional(nv.empleadorProperty());
		}
		
	}*/

	private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o, ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {

		if (ov != null) {
			experienciaTV.setItems(null);
			experiencia.unbind();
			eliminarBt.disableProperty().unbind();
		}

		if (nv != null) {
			experienciaTV.setItems(nv);
			experiencia.bind(experienciaTV.getSelectionModel().selectedItemProperty());
			eliminarBt.disableProperty().bind(Bindings.isEmpty(experienciaTV.getItems()));
		}
	}

	public final ObjectProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}
	

	public final Experiencia getExperiencia() {
		return this.experienciaProperty().get();
	}
	

	public final void setExperiencia(final Experiencia experiencia) {
		this.experienciaProperty().set(experiencia);
	}

	public BorderPane getView() {
		return view;
	}

}
