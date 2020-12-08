package dad.javafx.micv.personal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Nacionalidad;
import dad.javafx.micv.model.Personal;
import dad.javafx.micv.utils.LeerFichero;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();
	private ObjectProperty<Nacionalidad> nacionalidad = new SimpleObjectProperty<>();

	// view

	@FXML
	private GridPane view;

	@FXML
	private TextField identificacionText;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField apellidosText;
	
	@FXML
    private DatePicker fechaDate;

    @FXML
    private TextArea direccionText;

    @FXML
    private TextField codPostalText;

    @FXML
    private TextField localidadText;

    @FXML
    private ComboBox<String> paisCombo;

    @FXML
    private ListView<Nacionalidad> nacionalidadList;
    
    @FXML
    private Button añadirBt;

    @FXML
    private Button eliminarBt;
    
    private List<String> listaNacionalidades = new ArrayList<>();

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Combo de paises
			try {
				paisCombo.getItems().addAll(LeerFichero.leer("/csv/paises.csv"));
			} catch (Exception e) {
				App.error("ERROR", e.getLocalizedMessage());
				Platform.exit();
			}
				
			try {
				listaNacionalidades.addAll(LeerFichero.leer("/csv/nacionalidades.csv"));
			} catch (Exception e) {
				App.error("ERROR", e.getLocalizedMessage());
				Platform.exit();
			}

		this.personal.addListener((o, ov, nv) -> onPersonalChanged(o, ov, nv));
		//this.nacionalidad.addListener((o, ov, nv) -> onNacionalidadChanged(o, ov, nv));
		
	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
		if (ov != null) {
			identificacionText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			fechaDate.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionText.textProperty().unbindBidirectional(ov.direccionProperty());
			codPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			nacionalidadList.itemsProperty().unbind();
			paisCombo.valueProperty().unbindBidirectional(ov.paisProperty());
			nacionalidad.unbind();
			eliminarBt.disableProperty().unbind();
		}

		if (nv != null) {
			identificacionText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			fechaDate.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionText.textProperty().bindBidirectional(nv.direccionProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			nacionalidadList.itemsProperty().bind(nv.nacionalidadesProperty());
			paisCombo.valueProperty().bindBidirectional(nv.paisProperty());
			nacionalidad.bind(nacionalidadList.getSelectionModel().selectedItemProperty());
			eliminarBt.disableProperty().bind(Bindings.isEmpty(nacionalidadList.getItems()));
		}
		
	}
	
    @FXML
    void onActionEliminarNacionalidad(ActionEvent event) {
    	personal.get().getNacionalidades().remove(nacionalidad.get());
    }

    @FXML
    void onActionAñadirNacionalidad(ActionEvent event) {
    	ChoiceDialog<String> dialogo = new ChoiceDialog<>(listaNacionalidades.get(0), listaNacionalidades);
		
		dialogo.setTitle("Nueva nacionalidad");
		dialogo.setHeaderText("Añadir nacionalidad");
		dialogo.setContentText("Seleccione una nacionalidad");
		
		Optional<String> result = dialogo.showAndWait();
		
		if (result.isPresent()) {
			Nacionalidad nacionalidad = new Nacionalidad();
			nacionalidad.setDenominacion(result.get());
			
			personal.get().getNacionalidades().add(nacionalidad);
		}
    }
	
    
	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
