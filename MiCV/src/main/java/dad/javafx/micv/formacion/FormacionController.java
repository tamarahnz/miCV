package dad.javafx.micv.formacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.dialogos.DialogoExperiencia;
import dad.javafx.dialogos.DialogoTitulo;
import dad.javafx.micv.App;
import dad.javafx.micv.model.Experiencia;
import dad.javafx.micv.model.Formacion;
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

public class FormacionController implements Initializable{

	//model
	private ObjectProperty<Formacion> formacion = new SimpleObjectProperty<>();
	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Titulo> titulo = new SimpleObjectProperty<>();
	
	//view
	 @FXML
	 private BorderPane view;

	 @FXML
	 private TableView<Titulo> tituloTV;

	 @FXML
	 private TableColumn<Titulo, LocalDate> desdeTC;

	 @FXML
	 private TableColumn<Titulo, LocalDate> hastaTC;

	 @FXML
	 private TableColumn<Titulo, String> denominacionTC;

	 @FXML
	 private TableColumn<Titulo, String> organizadorTC;

	 @FXML
	 private Button añadirBt;

	 @FXML
	 private Button eliminarBt;

	 public FormacionController() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		}
		
		public void initialize(URL location, ResourceBundle resources) {
			//this.formacion.addListener((o, ov, nv) -> onFormacionChanged(o, ov, nv));
			desdeTC.setCellValueFactory(v -> v.getValue().desdeProperty());
			desdeTC.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
			hastaTC.setCellValueFactory(v -> v.getValue().hastaProperty());
			hastaTC.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
			denominacionTC.setCellValueFactory(v -> v.getValue().denominacionProperty());
			denominacionTC.setCellFactory(TextFieldTableCell.forTableColumn());
			organizadorTC.setCellValueFactory(v -> v.getValue().organizadorProperty());
			organizadorTC.setCellFactory(TextFieldTableCell.forTableColumn());
			
			this.titulos.addListener((o, ov, nv) -> onTituloChanged(o, ov,nv));
			
		}
	 
		private void onTituloChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov, ObservableList<Titulo> nv) {
		
		 if (ov != null) {
			 	tituloTV.setItems(null);
			 	titulo.unbind();
			 	eliminarBt.disableProperty().unbind();
				/*desdeTC.textProperty().unbindBidirectional(ov.desdeProperty());
				hastaTC.textProperty().unbindBidirectional(ov.hastaProperty());
				denominacionTC.textProperty().unbindBidirectional(ov.denominacionProperty());
				organizadorTC.textProperty().unbindBidirectional(ov.organizadorProperty());
				 */
		}

		if (nv != null) {
				tituloTV.setItems(nv);
				titulo.bind(tituloTV.getSelectionModel().selectedItemProperty());
				eliminarBt.disableProperty().bind(Bindings.isEmpty(tituloTV.getItems()));
				/*desdeTC.textProperty().bindBidirectional(nv.desdeProperty());
				hastaTC.textProperty().bindBidirectional(nv.hastaProperty());
				denominacionTC.textProperty().bindBidirectional(nv.denominacionProperty());
				organizadorTC.textProperty().bindBidirectional(nv.organizadorProperty());
				*/
		}
	}

	/*private void onFormacionChanged(ObservableValue<? extends Formacion> o, Formacion ov, Formacion nv) {
		 
		if (ov != null) {
				tituloTV.itemsProperty().unbind();
		}

		if (nv != null) {
				//TODO desbindear titulo
		}
	}*/

	//TODO añadir
	@FXML
	 void onActionAñadir(ActionEvent event) {
		DialogoTitulo dialogo = new DialogoTitulo();
		Optional<Titulo> resultado = dialogo.showAndWait();
		
		if (resultado.isPresent()) {
			titulos.get().add(resultado.get());
		}
	 }

	 @FXML
	 void onActionEliminar(ActionEvent event) {
		 String title = "Eliminar formación";
		 String cabecera = "¿Desea eliminar la formación?";
		 Titulo formacion = titulo.get();
			
		if (formacion != null && App.confirm(title, cabecera)) {
			titulos.get().remove(formacion);
		}
	 }

	
	public BorderPane getView() {
		return view;
	}

	public final ObjectProperty<Formacion> formacionProperty() {
		return this.formacion;
	}
	

	public final Formacion getFormacion() {
		return this.formacionProperty().get();
	}
	

	public final void setFormacion(final Formacion formacion) {
		this.formacionProperty().set(formacion);
	}
	

	public final ObjectProperty<Titulo> tituloProperty() {
		return this.titulo;
	}
	

	public final Titulo getTitulo() {
		return this.tituloProperty().get();
	}
	

	public final void setTitulo(final Titulo titulo) {
		this.tituloProperty().set(titulo);
	}
	
}
