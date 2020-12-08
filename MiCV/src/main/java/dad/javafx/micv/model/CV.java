package dad.javafx.micv.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CV {

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>(new Personal());
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<Contacto>(new Contacto());
	private ObjectProperty<Formacion> formacion = new SimpleObjectProperty<Formacion>(new Formacion());
	private ObjectProperty<Experiencia> experiencia = new SimpleObjectProperty<Experiencia>(new Experiencia());
	private ObjectProperty<Conocimientos> conocimientos = new SimpleObjectProperty<Conocimientos>(new Conocimientos());
	
	
	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
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

	public final ObjectProperty<Formacion> formacionProperty() {
		return this.formacion;
	}
	
	public final Formacion getFormacion() {
		return this.formacionProperty().get();
	}
	
	public final void setFormacion(final Formacion formacion) {
		this.formacionProperty().set(formacion);
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

	public final ObjectProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}

	public final Experiencia getExperiencia() {
		return this.experienciaProperty().get();
	}
	
	public final void setExperiencia(final Experiencia experiencia) {
		this.experienciaProperty().set(experiencia);
	}
	
}
