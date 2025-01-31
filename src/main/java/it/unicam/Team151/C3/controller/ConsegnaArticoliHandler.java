package it.unicam.Team151.C3.controller;

import it.unicam.Team151.C3.prenotazione.Armadietto;
import it.unicam.Team151.C3.prenotazione.Prenotazione;
import it.unicam.Team151.C3.prenotazione.Stato;
import it.unicam.Team151.C3.repositories.IRepositoryMaster;
import it.unicam.Team151.C3.utenti.Corriere;
import it.unicam.Team151.C3.util.ILoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
/**
 * Classe che rappresenta il caso d'uso 'Consegna Articoli'
 */
@Service
public class ConsegnaArticoliHandler {

	@Autowired
	IRepositoryMaster repositoryMaster;
	@Autowired
	ILoginChecker loginChecker;

	/**
	 * Metodo che permette al corriere di riempire l'armadietto assegnato con una prenotazione, cambaiandone lo stato in consegnato
	 */
	public void consegnaPrenotazione(Long idCorriere, Long idPrenotazione) {
		loginChecker.checkCorriere(idCorriere);
		if (repositoryMaster.getPrenotazioneRepository().findById(idPrenotazione).isEmpty())
			throw new NoSuchElementException("Nessuna prenotazione trovata.");
		Prenotazione prenotazione = repositoryMaster.getPrenotazioneRepository().findById(idPrenotazione).get();
		if (repositoryMaster.getRicevutaRepository().findByPrenotazione(prenotazione).isEmpty())
			throw new NoSuchElementException("Nessuna ricevuta trovata.");
		prenotazione.setRicevuta(repositoryMaster.getRicevutaRepository().findByPrenotazione(prenotazione).get());
		repositoryMaster.getPaccoRepository().findAllByPrenotazione(prenotazione).forEach(prenotazione.getPacchi()::add);
		if(!prenotazione.getStato().equals(Stato.Ritirato))
			throw new IllegalStateException("Errore di stato: la prenotazione non è in stato di ritirato");
		prenotazione.setStato(Stato.Consegnato);
		repositoryMaster.getPrenotazioneRepository().save(prenotazione);
		if (repositoryMaster.getArmadiettoRepository().findByCodice(prenotazione.getRicevuta().getCodice()).isEmpty())
			throw new NoSuchElementException("Nessun armadietto trovato.");
		Armadietto armadietto = repositoryMaster.getArmadiettoRepository().findByCodice(prenotazione.getRicevuta().getCodice()).get();
		armadietto.setPrenotazione(prenotazione);
		repositoryMaster.getArmadiettoRepository().save(armadietto);
	}
}