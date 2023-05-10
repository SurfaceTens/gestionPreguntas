package es.mdef.gestionPreguntas.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.gestionPreguntas.entidades.Familia;
import es.mdef.gestionPreguntas.entidades.Usuario;

@Relation(itemRelation = "pregunta")
public class PreguntaPostModel extends RepresentationModel<PreguntaPostModel> {

	private String enunciado;
	private Usuario usuario;
	private Familia familia;

	public String getEnunciado() {
		return enunciado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Familia getFamilia() {
		return familia;
	}

	@Override
	public String toString() {
		return "PreguntaPostModel [enunciado=" + enunciado + ", usuario=" + usuario + ", familia=" + familia + "]";
	}

}
