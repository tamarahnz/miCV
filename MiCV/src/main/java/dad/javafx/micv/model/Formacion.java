package dad.javafx.micv.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Formacion {

	private ListProperty<Titulo> titulo = new SimpleListProperty<>(FXCollections.observableArrayList());

	
	public final ListProperty<Titulo> tituloProperty() {
		return this.titulo;
	}
	
	public final ObservableList<Titulo> getTitulo() {
		return this.tituloProperty().get();
	}

	public final void setTitulo(final ObservableList<Titulo> titulo) {
		this.tituloProperty().set(titulo);
	}

}
