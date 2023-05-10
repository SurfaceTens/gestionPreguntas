package es.mdef.gestionPreguntas.REST;

import java.util.List;
import java.util.stream.Collectors;

//import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import es.mdef.gestionPreguntas.GestionPreguntasApplication;
import es.mdef.gestionPreguntas.entidades.Administrador;
import es.mdef.gestionPreguntas.entidades.Familia;
import es.mdef.gestionPreguntas.entidades.NoAdministrador;
import es.mdef.gestionPreguntas.entidades.Pregunta;
import es.mdef.gestionPreguntas.entidades.Usuario;
import es.mdef.gestionPreguntas.entidades.Usuario.Rol;
import es.mdef.gestionPreguntas.repositorios.UsuarioRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final UsuarioAssembler assembler;
	private final UsuarioListaAssembler listaAssembler;
	private final PreguntaListaAssembler prListaAssembler;
	private final FamiliaListaAssembler famListaAssembler;
	private final Logger log;

	UsuarioController(UsuarioRepositorio repositorio, UsuarioAssembler assembler, UsuarioListaAssembler listaAssembler,
			PreguntaListaAssembler prListaAssembler, FamiliaListaAssembler famListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.prListaAssembler = prListaAssembler;
		this.famListaAssembler = famListaAssembler;
		log = GestionPreguntasApplication.log;
	}

	@GetMapping("{id}")
	public UsuarioModel one(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}

	@GetMapping("/buscarusuario")
	public UsuarioModel getByUsername(@RequestParam(value = "username") String username) {
		Usuario usuario = repositorio.findByUsername(username)
				.orElseThrow(() -> new RegisterNotFoundException(username));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}

	@GetMapping
	public CollectionModel<UsuarioListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntasDeUsuario(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		return prListaAssembler.toCollection(usuario.getPreguntas());
	}

	@GetMapping("{id}/familias")
	public CollectionModel<FamiliaListaModel> familiasDeUsuario(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		List<Familia> familias = usuario.getPreguntas().stream().map(Pregunta::getFamilia).distinct()
				.collect(Collectors.toList());
		return famListaAssembler.toCollection(familias);
	}

	@PostMapping
	public UsuarioModel add(@Valid @RequestBody UsuarioPostModel model) {
		Usuario usuario = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + usuario);
		return assembler.toModel(usuario);
	}

	@PutMapping("{id}")
	public UsuarioModel edit(@Valid @PathVariable Long id, @RequestBody UsuarioPutModel model) {
		Usuario usuario = repositorio.findById(id).map(usu -> {
			log.info("PUT MODEL" + model);
			String nombre = model.getNombre();
			String username = model.getNombre();

			if (model.getRol() == Rol.Administrator) {
				Administrador admin = new Administrador();

				if (model.getTelefono() != null) {
					repositorio.actualizarUsuarioAdmin(nombre, username, model.getTelefono(), id);
				}

				admin.setTelefono(model.getTelefono());
				admin.setNombre(model.getNombre());
				admin.setUsername(model.getUsername());
				admin.setPassword(usu.getPassword());

				usu = admin;
			} else if (model.getRol() == Rol.noAdministrator) {
				NoAdministrador noAdmin = new NoAdministrador();

				if (model.getDpto() != null && model.getTipo() != null) {
					repositorio.actualizarUsuarioNoAdmin(nombre, username, model.getDpto().ordinal(),
							model.getTipo().ordinal(), id);
				}
				noAdmin.setDpto(model.getDpto());
				noAdmin.setTipo(model.getTipo());
				noAdmin.setNombre(model.getNombre());
				noAdmin.setUsername(model.getUsername());
				noAdmin.setPassword(usu.getPassword());
				usu = noAdmin;
			}
			usu.setId(id);
			return repositorio.save(usu);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Usuario"));
		log.info("Actualizado ---->>>> " + usuario);
		return assembler.toModel(usuario);
	}

	@PutMapping("{id}/password")
	public void editPassword(@Valid @PathVariable Long id, @RequestBody String password) {
		log.info("Nueva password " + password);

		Usuario usuario = repositorio.findById(id).map(usu -> {
			usu.setPassword(/* new BCryptPasswordEncoder().encode( */password)/* ) */;
			return repositorio.save(usu);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Usuario"));
		log.info("Actualizada constraseña " + usuario);
	}

	@PatchMapping("{id}/cambiarContrasena")
	public UsuarioModel edit(@Valid @PathVariable Long id, @RequestBody String newPassword) {
		Usuario usuario = repositorio.findById(id).map(usu -> {
			usu.setPassword(/* new BCryptPasswordEncoder().encode( */newPassword)/* ) */;
			return repositorio.save(usu);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Usuario eliminado: " + id);
		repositorio.findById(id).ifPresent(usr -> {
			repositorio.deleteById(id);
		});
	}

}