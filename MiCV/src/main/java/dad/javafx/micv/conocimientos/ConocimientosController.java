package dad.javafx.micv.conocimientos;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.dialogos.DialogoConocimiento;
import dad.javafx.dialogos.DialogoIdioma;
import dad.javafx.micv.App;
import dad.javafx.micv.model.Conocimientos;
import dad.javafx.micv.model.Idioma;
//import dad.javafx.micv.model.Habilidades;
import dad.javafx.micv.model.Nivel;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable {

	//model
	
	private ObjectProperty<Conocimientos> conocimientos = new SimpleObjectProperty<>();
	private ListProperty<Conocimientos> conocimiento = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	//view
	
	@FXML
    private BorderPane view;

    @FXML
    private TableView<Conocimientos> conocimientosTV;

    @FXML
    private TableColumn<Conocimientos, String> denominacionTC;

    @FXML
    private TableColumn<Conocimientos, Nivel> nivelTC;

    @FXML
    private Button añadirConocimientoBt;

    @FXML
    private Button añadirIdiomaBt;

    @FXML
    private Button eliminarBt;

    //TODO añadir
    @FXML
    void onActionAñadirConocimiento(ActionEvent event) {
    	DialogoConocimiento dialogo = new DialogoConocimiento();
    	Optional<Conocimientos> resultado = dialogo.showAndWait();
    	
    	if (resultado.isPresent()) {
    		conocimiento.get().add(resultado.get());
    	}
    	
    }

    @FXML
    void onActionAñadirIdioma(ActionEvent event) {
    	DialogoIdioma dialogo = new DialogoIdioma();
    	Optional<Idioma> resultado = dialogo.showAndWait();
    	
    	if (resultado.isPresent()) {
    		conocimiento.get().add(resultado.get());
    	}
    }

    @FXML
    void onActionEliminar(ActionEvent event) {
    	String titulo = "Eliminar conocimiento";
		String cabecera = "¿Desea eliminar el conocimiento?";
		Conocimientos conoci = conocimientos.get();

		if (conoci != null && App.confirm(titulo, cabecera)) {
			conocimiento.get().remove(conoci);
		}
    }
    
    public ConocimientosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		denominacionTC.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelTC.setCellValueFactory(v -> v.getValue().nivelProperty());

		denominacionTC.setCellFactory(TextFieldTableCell.forTableColumn());
		nivelTC.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));

		
		this.conocimiento.addListener((o, ov, nv) -> onConocimientosChanged(o, ov, nv));
		//this.habilidades.addListener((o, ov, nv) -> onHabilidadesChanged(o, ov, nv));
		
	}

	/*private void onHabilidadesChanged(ObservableValue<? extends Habilidades> o, Habilidades ov, Habilidades nv) {
		
		if (ov != null) {
			denominacionTC.textProperty().unbindBidirectional(ov.denominacionProperty());
			nivelTC.textProperty().unbindBidirectional(ov.nivelProperty());
		}

		if (nv != null) {
			denominacionTC.textProperty().bindBidirectional(nv.denominacionProperty());
			nivelTC.textProperty().bindBidirectional(nv.nivelProperty());
		}
	}*/

	private void onConocimientosChanged(ObservableValue<? extends ObservableList<Conocimientos>> o,
			ObservableList<Conocimientos> ov, ObservableList<Conocimientos> nv) {
		
		if (ov != null) {
			conocimientosTV.setItems(null);
			conocimiento.unbind();
			eliminarBt.disableProperty().unbind();
		}

		if (nv != null) {
			conocimientosTV.setItems(nv);
			conocimientos.bind(conocimientosTV.getSelectionModel().selectedItemProperty());
			eliminarBt.disableProperty().bind(Bindings.isEmpty(conocimientosTV.getItems()));
		}
	}

	public final ObjectProperty<Conocimientos> conocimientosProperty() {
		return this.conocimientos;
	}
	

	public final Conocimientos getConocimientos() {
		return this.conocimientosProperty().get();
	}
	

	public final void setConocimientos(final Conocimientos conocimientos) {
		this.conocimientosProperty().set(conocimientos);
	}

	public BorderPane getView() {
		return view;
	}

}
