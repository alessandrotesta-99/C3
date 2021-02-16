package it.unicam.Team151.C3.view;

import it.unicam.Team151.C3.articoli.Categoria;
import it.unicam.Team151.C3.articoli.DescrizioneArticolo;
import it.unicam.Team151.C3.controller.ConfermaAcquistoHandler;
import it.unicam.Team151.C3.controller.GestioneArticoliHandler;
import it.unicam.Team151.C3.controller.GestionePuntiVenditaHandler;
import it.unicam.Team151.C3.controller.LogoutHandler;
import it.unicam.Team151.C3.prenotazione.*;
import it.unicam.Team151.C3.puntoVendita.Pacco;
import it.unicam.Team151.C3.puntoVendita.PuntoVendita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commerciante")
public class ICommerciante implements IUtenteAutenticato{

	@Autowired
	GestionePuntiVenditaHandler gestionePuntiVenditaHandler;
	@Autowired
	ConfermaAcquistoHandler confermaAcquistoHandler;
	@Autowired
	GestioneArticoliHandler gestioneArticoliHandler;
	@Autowired
	private GestorePrenotazione gestorePrenotazione;


	@Autowired
	LogoutHandler logoutHandler;

	//sono passi che non abbiamo scritto nel caso d'uso.
	@PostMapping("confermaAcquisto")
	public List<Pacco> confermaAcquisto(@RequestParam Long idPrenotazione) {
		return confermaAcquistoHandler.confermaAcquisto(idPrenotazione);
	}

	@PostMapping("confermaPagamento")
	public void confermaPagamento(@RequestParam Long idPrenotazione, @RequestParam Long idPacco) {
		confermaAcquistoHandler.confermaPagamento(idPrenotazione, idPacco);
	}

	public void aggiungiArticolo() {
		// TODO - implement ICommerciante.aggiungiArticolo
		throw new UnsupportedOperationException();
	}

	@PostMapping("aggiungiArticolo")
	public DescrizioneArticolo inserimentoDatiArticoloDaAggiungere(@RequestParam String nome,
													@RequestParam String descrizione,
													@RequestParam double prezzo,
													@RequestParam int quantita,
													@RequestParam Long idPuntoVendita,
													@RequestParam Long idCategoria) {
	return gestioneArticoliHandler.inserimentoDatiArticoloDaAggiungere(nome, descrizione, prezzo, quantita, idPuntoVendita, idCategoria);
	}

	@PostMapping("getArticoloDaModificare")
	public List<DescrizioneArticolo> modificaArticolo(@RequestParam Long idCommerciante) {
		return gestioneArticoliHandler.getArticoli(idCommerciante);
	}

	@PostMapping("scegliArticoloDaModificare")
	public void scegliArticolo(@RequestParam Long idArticolo) {
		gestioneArticoliHandler.scegliArticolo(idArticolo);
	}

	@PostMapping("modificareArticolo")
	public void inserimentoDatiArticoloDaModificare(@RequestParam String nome, @RequestParam String descrizione,
													@RequestParam double prezzo, @RequestParam int quantita,
													@RequestParam PuntoVendita puntoVendita,
													@RequestParam Categoria categoria) {
		gestioneArticoliHandler.inserimentoDatiArticoloDaModificare(nome, descrizione, prezzo, quantita, puntoVendita, categoria);
	}

	@PostMapping("getArticoloDaRimuovere")
	public List<DescrizioneArticolo> rimuoviArticolo(@RequestParam Long idCommerciante) {
		return gestioneArticoliHandler.getArticoli(idCommerciante);
	}

	@PostMapping("rimuoviArticolo")
	public void rimozioneArticolo(@RequestParam Long idDescArticolo) {
		gestioneArticoliHandler.rimozioneArticolo(idDescArticolo);
	}

	@PostMapping("getPuntiVendita")
	public List<PuntoVendita> getPuntiVendita(@RequestParam Long idCommerciante){
		return gestionePuntiVenditaHandler.getPuntiVendita(idCommerciante);
	}

	@PostMapping("aggiungiPuntoVendita")
	public void aggiungiPuntoVendita(@RequestParam Long idCommerciante, @RequestParam String nome, @RequestParam String ubicazione){
		gestionePuntiVenditaHandler.aggiungiPuntoVendita(idCommerciante, nome, ubicazione);
	}

	@PostMapping("modificaPuntoVendita")
	public void modificaPuntoVendita(@RequestParam Long idPuntoVendita, @RequestParam String nome, @RequestParam String ubicazione) {
		gestionePuntiVenditaHandler.modificaPuntoVendita(idPuntoVendita, nome, ubicazione);
	}

	@PostMapping("rimuoviPuntoVendita")
	public void rimuoviPuntoVendita(@RequestParam Long idPuntoVendita) {
		gestionePuntiVenditaHandler.rimuoviPuntoVendita(idPuntoVendita);
	}

	@Override
	@PostMapping("logout")
	public void logout(@RequestParam Long id) {
		logoutHandler.logout(id);
	}
}