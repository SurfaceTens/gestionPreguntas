package es.mdef.gestionPreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import es.mdef.gestionPreguntas.entidades.Usuario.Rol;
import es.mdef.gestionPreguntas.entidades.NoAdministrador.Dpto;
import es.mdef.gestionPreguntas.entidades.NoAdministrador.Tipo;

@Relation(itemRelation = "usuario")
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	private String nombre;
	private String username;
	private String password;
	private Rol rol;
	// atributo del administrador
	private String telefono;
	// atributos del noAdministrador
	private Dpto dpto;
	private Tipo tipo;
	// hemos incluido en el modelo el numero de preguntas del usuario
	private int numPreguntas;

	public int getNumPreguntas() {
		return numPreguntas;
	}

	public void setNumPreguntas(int numPreguntas) {
		this.numPreguntas = numPreguntas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setTelefono(String tlf) {
		this.telefono = tlf;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setDpto(Dpto dpto) {
		this.dpto = dpto;
	}

	public Dpto getDpto() {
		return dpto;
	}

	@Override
	public String toString() {
		return "UsuarioModel [nombre=" + nombre + ", username=" + username + ", password=" + password + ", rol=" + rol
				+ ", telefono=" + telefono + ", dpto=" + dpto + ", tipo=" + tipo + ", numPreguntas=" + numPreguntas
				+ "]";
	}

}