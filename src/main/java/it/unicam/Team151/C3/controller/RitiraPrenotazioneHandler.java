package it.unicam.Team151.C3.controller;

import it.unicam.Team151.C3.prenotazione.Armadietto;
import it.unicam.Team151.C3.prenotazione.PuntoConsegna;
import it.unicam.Team151.C3.repositories.ArmadiettoRepository;
import it.unicam.Team151.C3.repositories.IRepositoryMaster;
import it.unicam.Team151.C3.repositories.PuntoConsegnaRepository;
import it.unicam.Team151.C3.servizioClienti.ServizioClienti;
import it.unicam.Team151.C3.utenti.Cliente;
import it.unicam.Team151.C3.util.ILoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

/**
 * Classe che rappresenta il caso d'uso 'Ritira Prenotazione'
 */
@Service
public class RitiraPrenotazioneHandler {

	@Autowired
	ServizioClienti servizioClienti;
	@Autowired
	IRepositoryMaster repositoryMaster;
	@Autowired
	ILoginChecker loginChecker;

	/**
	 * Metodo che permette al cliente di ritirare la sua prenotazione e che libera l'armadietto, rendendolo disponibile
	 * per la prossima prenotazione
	 */
	public void ritiraPrenotazione(Long idCliente, Long idPuntoConsegna, Long idArmadietto) {
		loginChecker.checkCliente(idCliente);
		PuntoConsegna puntoConsegna = getPuntoConsegna(idPuntoConsegna);
		if (repositoryMaster.getArmadiettoRepository().findById(idArmadietto).isEmpty())
			throw new NoSuchElementException("Nessun armadietto trovato.");
		Armadietto armadietto = repositoryMaster.getArmadiettoRepository().findById(idArmadietto).get();
		puntoConsegna.liberaArmadietto(armadietto);
		repositoryMaster.getArmadiettoRepository().save(armadietto);
		servizioClienti.richiestaFeedback();
	}

	/**
	 * Metodo che restituisce l'armadietto con quel codice, se il codice è giusto
	 */
	public Armadietto checkCodice(Long idPuntoConsegna, int codice) {
		PuntoConsegna puntoConsegna = getPuntoConsegna(idPuntoConsegna);
		Armadietto armadietto = puntoConsegna.checkCodice(codice);
		if (armadietto == null)
			throw new IllegalArgumentException("Codice errato.");
		return armadietto;
    }

	private PuntoConsegna getPuntoConsegna(Long idPuntoConsegna) {
		if (repositoryMaster.getPuntoConsegnaRepository().findById(idPuntoConsegna).isEmpty())
			throw new NoSuchElementException("Nessun punto consgena trovato.");
		PuntoConsegna puntoConsegna = repositoryMaster.getPuntoConsegnaRepository().findById(idPuntoConsegna).get();
		puntoConsegna.getArmadietti().addAll(repositoryMaster.getArmadiettoRepository().findAllByPuntoConsegna(puntoConsegna));
		return puntoConsegna;
	}
}