package es.mdef.gestionPreguntas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionPreguntas.entidades.Familia;

public interface FamiliaRepositorio extends JpaRepository<Familia, Long> {

}