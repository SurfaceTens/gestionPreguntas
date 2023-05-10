package es.mdef.gestionPreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.mdef.gestionPreguntas.entidades.Administrador;
import es.mdef.gestionPreguntas.entidades.NoAdministrador;
import es.mdef.gestionPreguntas.entidades.Usuario;
import es.mdef.gestionPreguntas.entidades.Usuario.Rol;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

	@Override
	public UsuarioModel toModel(Usuario entity) {
		UsuarioModel model = new UsuarioModel();
		model.setNombre(entity.getNombre());
		model.setUsername(entity.getUsername());
		model.setRol(entity.getRol());
		int numPreguntas = entity.getPreguntas() != null ? entity.getPreguntas().size() : 0;
		model.setNumPreguntas(numPreguntas);

		if (entity.getRol() == Rol.Administrator) {
			model.setTelefono(((Administrador) entity).getTelefono());
		} else if (entity.getRol() == Rol.noAdministrator) {
			model.setDpto(((NoAdministrador) entity).getDpto());
			model.setTipo(((NoAdministrador) entity).getTipo());
		}
		model.add(linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel());
		model.add(linkTo(methodOn(UsuarioController.class).preguntasDeUsuario(entity.getId())).withRel("preguntas"));
		model.add(linkTo(methodOn(UsuarioController.class).familiasDeUsuario(entity.getId())).withRel("familias"));

		return model;
	}

	public Usuario toEntity(UsuarioPostModel model) {
		Usuario usuario;

		switch (model.getRol()) {
		case Administrator:
			Administrador admin = new Administrador();
			admin.setTelefono(model.getTelefono());
			usuario = admin;
			break;
		case noAdministrator:
			NoAdministrador noAdmin = new NoAdministrador();
			noAdmin.setTipo(model.getTipo());
			noAdmin.setDpto(model.getDpto());
			usuario = noAdmin;
			break;
		default:
			usuario = new Usuario();
		}

		usuario.setPassword(/*new BCryptPasswordEncoder().encode(*/model.getPassword())/*)*/;
		usuario.setNombre(model.getNombre());
		usuario.setUsername(model.getUsername());
		return usuario;
	}

	public Usuario toEntity(UsuarioPutModel model) {
		Usuario usuario;

		switch (model.getRol()) {
		case Administrator:
			Administrador admin = new Administrador();
			admin.setTelefono(model.getTelefono());
			usuario = admin;
			break;
		case noAdministrator:
			NoAdministrador noAdmin = new NoAdministrador();
			noAdmin.setTipo(model.getTipo());
			noAdmin.setDpto(model.getDpto());
			usuario = noAdmin;
			break;
		default:
			usuario = new Usuario();
		}

		usuario.setNombre(model.getNombre());
		usuario.setUsername(model.getUsername());
		return usuario;
	}
}