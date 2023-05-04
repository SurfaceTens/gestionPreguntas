package es.mdef.gestionPreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import es.mdef.gestionPreguntas.entidades.Usuario.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import es.mdef.gestionPreguntas.entidades.NoAdministrador.Dpto;
import es.mdef.gestionPreguntas.entidades.NoAdministrador.Tipo;

@Relation(itemRelation = "usuario")
public class UsuarioPostModel extends RepresentationModel<UsuarioPostModel> {

	private String nombre;
	private String username;
	private String password;
	// controlamos aquí el rol, ya que en la entidad no existe
	@NotNull(message = "El rol es obligatorio")
	private Rol rol;
	// atributo del administrador
	private String telefono;
	// atributos del noAdministrador
	private Dpto dpto;
	private Tipo tipo;

	public String getNombre() {
		return nombre;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Rol getRol() {
		return rol;
	}

	public String getTelefono() {
		return telefono;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Dpto getDpto() {
		return dpto;
	}

	@Override
	public String toString() {
		return "UsuarioPostModel [Nombre=" + nombre + ", UserName=" + username + "Pass" + password + "]";
	}

}