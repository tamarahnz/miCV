package dad.javafx.micv.contacto;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Contacto;
import dad.javafx.micv.model.Email;
import dad.javafx.micv.model.Telefono;
import dad.javafx.micv.model.TipoTelefono;
import dad.javafx.micv.model.Web;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class ContactoController implements Initializable {

	//model
	
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();
	private ObjectProperty<Telefono> telefono = new SimpleObjectProperty<>();
	private ObjectProperty<Web> web = new SimpleObjectProperty<>();
	private ObjectProperty<Email> email = new SimpleObjectProperty<>();
	
	//view
	
	@FXML
    private SplitPane view;

	@FXML
    private TableView<Telefono> telefonosTV;

    @FXML
    private TableColumn<Telefono, String> numeroTC;

    @FXML
    private TableColumn<Telefono, TipoTelefono> tipoTC;
    
    @FXML
    private TableView<Email> correoTV;

    @FXML
    private TableColumn<Email, String> emailTC;
    
    @FXML
    private TableView<Web> websTV;

    @FXML
    private TableColumn<Web, String> urlTC;

    @FXML
    private Button eliminarTlfBt;
    
    @FXML
    private Button eliminarCorreoBt;
    
    @FXML
    private Button eliminarWebBt;
    
    
	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		//Telefono
		numeroTC.setCellValueFactory(v -> v.getValue().numeroProperty());
		numeroTC.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoTC.setCellValueFactory(v -> v.getValue().tipoProperty());
		tipoTC.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		
		//Correo
		emailTC.setCellValueFactory(v -> v.getValue().emailProperty());
		emailTC.setCellFactory(TextFieldTableCell.forTableColumn());
		
		//Web
		urlTC.setCellValueFactory(v -> v.getValue().urlProperty());
		urlTC.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
	}

	/*private void onEmailChanged(ObservableValue<? extends Email> o, Email ov, Email nv) {
		
		if (ov != null) {
			emailTC.textProperty().unbindBidirectional(ov.emailProperty());
		}

		if (nv != null) {
			emailTC.textProperty().bindBidirectional(nv.emailProperty());
		}
	}

	private void onWebChanged(ObservableValue<? extends Web> o, Web ov, Web nv) {
		
		if (ov != null) {
			urlTC.textProperty().unbindBidirectional(ov.urlProperty());
		}

		if (nv != null) {
			urlTC.textProperty().bindBidirectional(nv.urlProperty());
		}
	}

	private void onTelefonoChanged(ObservableValue<? extends Telefono> o, Telefono ov, Telefono nv) {
		
		if (ov != null) {
			numeroTC.textProperty().unbindBidirectional(ov.numeroProperty());
			tipoTC.textProperty().unbindBidirectional(ov.tipoProperty());
		}

		if (nv != null) {
			numeroTC.textProperty().bindBidirectional(nv.numeroProperty());
			//tipoTC.itemsProperty().bind(nv.tipoProperty());
		}
	}*/

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefonosTV.itemsProperty().unbind();
			telefono.unbind();
			eliminarTlfBt.disableProperty().unbind();
			correoTV.itemsProperty().unbind();
			email.unbind();
			eliminarCorreoBt.disableProperty().unbind();
			websTV.itemsProperty().unbind();
			web.unbind();
			eliminarWebBt.disableProperty().unbind();
		}

		if (nv != null) {
			telefonosTV.itemsProperty().bind(nv.telefonosProperty());
			telefono.bind(telefonosTV.getSelectionModel().selectedItemProperty());
			eliminarTlfBt.disableProperty().bind(Bindings.isEmpty(telefonosTV.getItems()));
			correoTV.itemsProperty().bind(nv.emailsProperty());
			email.bind(correoTV.getSelectionModel().selectedItemProperty());
			eliminarCorreoBt.disableProperty().bind(Bindings.isEmpty(correoTV.getItems()));
			websTV.itemsProperty().bind(nv.websProperty());
			web.bind(websTV.getSelectionModel().selectedItemProperty());
			eliminarWebBt.disableProperty().bind(Bindings.isEmpty(websTV.getItems()));
		}
		
	}

	
	//Telefono
	@FXML
    void onActionAñadirTlf(ActionEvent event) {
		Dialog<Pair<String,TipoTelefono>> dialog = new Dialog();
		dialog.setTitle("Nuevo teléfono");
		dialog.setHeaderText("Introduzca el nuevo número de teléfono.");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		ButtonType aceptarButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(aceptarButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField numero = new TextField();
		numero.setPromptText("Número");
		ComboBox<TipoTelefono> tipoTelefonoCB = new ComboBox();
		tipoTelefonoCB.getItems().addAll(TipoTelefono.values());
		tipoTelefonoCB.setPromptText("Seleccione un tipo");
		
		Node aceptarButton = dialog.getDialogPane().lookupButton(aceptarButtonType);
		aceptarButton.setDisable(true);

		aceptarButton.disableProperty().bind(
				numero.textProperty().isEmpty().or(
				tipoTelefonoCB.valueProperty().isNull()));
		
		grid.add(new Label("Número:"), 0, 0);
		grid.add(numero, 1, 0);
		grid.add(new Label("Tipo:"), 0, 1);
		grid.add(tipoTelefonoCB, 1, 1);

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> numero.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == aceptarButtonType) {
		        return new Pair<>(numero.getText(), tipoTelefonoCB.getSelectionModel().getSelectedItem());
		    }
		    return null;
		});

		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();

		if (result.isPresent()) {
			Telefono telefono = new Telefono();
			telefono.setNumero(result.get().getKey());
			telefono.setTipo(result.get().getValue());
			contacto.get().getTelefonos().add(telefono);
		}
		
    }

    @FXML
    void onActionEliminarTlf(ActionEvent event) {
    	String titulo = "Eliminar teléfono";
    	String cabecera = "¿Desea eliminar el teléfono?";
    	Telefono tlf = telefono.get();
    	
    	if (tlf != null && App.confirm(titulo, cabecera)) {
    		contacto.get().getTelefonos().remove(tlf);
    	}
    	
    }
    
    //Correo
    @FXML
    void onActionAñadirCorreo(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
			dialog.getEditor().textProperty().isEmpty()
		);
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Email correo = new Email();
			correo.setEmail(result.get());
			contacto.get().emailsProperty().add(correo);
		}
    }

    @FXML
    void onActionEliminarCorreo(ActionEvent event) {
    	String titulo = "Eliminar dirección de correo";
    	String cabecera = "¿Desea eliminar la dirección de correo?";
    	Email correo = email.get();
    	
    	if (correo != null && App.confirm(titulo, cabecera)) {
    		contacto.get().getEmails().remove(correo);
    	}
    }
    
    //Web
    @FXML
    void onActionAñadirWeb(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL:");
		dialog.initOwner(App.getPrimaryStage());
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
			dialog.getEditor().textProperty().isEmpty()
		);
		
		dialog.getEditor().setText("http://");
		
		Platform.runLater(() -> dialog.getEditor().requestFocus());
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Web url = new Web();
			url.setUrl(result.get());
			contacto.get().websProperty().add(url);
		}
    }
    
    @FXML
    void onActionEliminarWeb(ActionEvent event) {
    	String titulo = "Eliminar dirección web";
    	String cabecera = "¿Desea eliminar la dirección web?";
    	Web url = web.get();
    	
    	if (url != null && App.confirm(titulo, cabecera)) {
    		contacto.get().getWebs().remove(url);
    	}
    }
    

	public SplitPane getView() {
		return view;
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}
	

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}

	public final ObjectProperty<Telefono> telefonoProperty() {
		return this.telefono;
	}
	

	public final Telefono getTelefono() {
		return this.telefonoProperty().get();
	}
	

	public final void setTelefono(final Telefono telefono) {
		this.telefonoProperty().set(telefono);
	}
	

	public final ObjectProperty<Web> webProperty() {
		return this.web;
	}
	

	public final Web getWeb() {
		return this.webProperty().get();
	}
	

	public final void setWeb(final Web web) {
		this.webProperty().set(web);
	}
	

	public final ObjectProperty<Email> emailProperty() {
		return this.email;
	}
	

	public final Email getEmail() {
		return this.emailProperty().get();
	}
	

	public final void setEmail(final Email email) {
		this.emailProperty().set(email);
	}
		
}
