package it.unicam.Team151.C3.controller;

import it.unicam.Team151.C3.articoli.*;
import it.unicam.Team151.C3.puntoVendita.GestorePuntoVendita;
import it.unicam.Team151.C3.puntoVendita.PuntoVendita;
import it.unicam.Team151.C3.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RicercaArticoliHandler {

	@Autowired
	CatalogoArticoli catalogoArticoli;
	@Autowired
	GestorePuntoVendita gestorePuntoVendita;
	@Autowired
	CategoriaRepository categoriaRepository;

	public List<Categoria> ricercaArticoliCategoria() {
		return catalogoArticoli.getCategorie();
	}


	public List<DescrizioneArticolo> scegliCategoria(Long idCategoria) {
		return catalogoArticoli.getArticoliPerCategoria(categoriaRepository.findById(idCategoria).get());
	}

	public List<PuntoVendita> ricercaArticoliPuntoVendita() {
		return gestorePuntoVendita.getPuntiVendita();
	}

	public List<DescrizioneArticolo> scegliPuntoVendita(Long idPuntoVendita) {
		return new ArrayList<>(catalogoArticoli.getArticoliPerPuntoVendita(gestorePuntoVendita.get(idPuntoVendita)));
	}

}