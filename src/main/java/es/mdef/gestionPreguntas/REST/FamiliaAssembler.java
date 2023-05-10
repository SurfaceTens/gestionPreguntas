package es.mdef.gestionPreguntas.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntas.entidades.Familia;

@Component
public class FamiliaAssembler implements RepresentationModelAssembler<Familia, FamiliaModel> {

	@Override
	public FamiliaModel toModel(Familia entity) {
		FamiliaModel model = new FamiliaModel();

		model.setEnunciado(entity.getEnunciado());
		int tamanio = entity.getPreguntas() != null ? entity.getPreguntas().size() : 0;
		model.setTamanio(tamanio);

		model.add(linkTo(methodOn(FamiliaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(FamiliaController.class).preguntasDeFamilia(entity.getId())).withRel("preguntas"),
				linkTo(methodOn(FamiliaController.class).usuariosDeFamilia(entity.getId())).withRel("usuarios"));
		return model;
	}

	public Familia toEntity(FamiliaPostModel model) {
		Familia familia = new Familia();
		familia.setEnunciado(model.getEnunciado());
		return familia;
	}
}
