package it.unicam.Team151.C3.puntoVendita;

import it.unicam.Team151.C3.articoli.ArticoloCarrello;
import it.unicam.Team151.C3.articoli.Carrello;
import it.unicam.Team151.C3.articoli.Categoria;
import it.unicam.Team151.C3.articoli.DescrizioneArticolo;
import it.unicam.Team151.C3.prenotazione.Prenotazione;
import it.unicam.Team151.C3.prenotazione.PuntoConsegna;
import it.unicam.Team151.C3.prenotazione.Stato;
import it.unicam.Team151.C3.utenti.Cliente;
import it.unicam.Team151.C3.utenti.Commerciante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaccoTest {

    Cliente rossi;
    Carrello carrelloRossi;
    Categoria categoria1;
    Categoria categoria2;
    Commerciante verdi;
    PuntoVendita puntoVendita1;
    PuntoVendita puntoVendita2;
    DescrizioneArticolo descrizioneArticolo1;
    DescrizioneArticolo descrizioneArticolo2;
    ArticoloCarrello articoloCarrello1;
    ArticoloCarrello articoloCarrello2;
    PuntoConsegna puntoConsegna;
    Prenotazione prenotazione;
    Pacco pacco1;
    Pacco pacco2;

    @BeforeEach
    void init(){
        rossi= new Cliente("Claudio","Rossi","Via Sonnino 17", "Cliente","claudioRossi@gmail.com", "termosifone");
        verdi= new Commerciante("Tommaso","Verdi","Viale Europa 104", "Commerciante","tommyVerdi@gmail.com", "scatola");
        carrelloRossi= new Carrello(rossi);
        categoria1= new Categoria("Ferramenta","Oggetti che si trovano in una ferramenta");
        categoria2= new Categoria("Elettronica","Oggettini elettronici");
        puntoVendita1= new PuntoVendita(verdi,"Da tommaso", "Via nazareth 33");
        puntoVendita2= new PuntoVendita(verdi, "Da rosa","Via recanati 56");
        descrizioneArticolo1= new DescrizioneArticolo("Cacciavite a stella","cacciavite",46.0, 3,puntoVendita1,categoria1);
        descrizioneArticolo2= new DescrizioneArticolo("Laptop Samsung","Samsung Galaxy Laptop 4", 450.0,10,puntoVendita2,categoria2);
        articoloCarrello1= new ArticoloCarrello(descrizioneArticolo1,2,carrelloRossi);
        articoloCarrello2= new ArticoloCarrello(descrizioneArticolo2,3,carrelloRossi);
        carrelloRossi.getArticoliCarrello().add(articoloCarrello1);
        carrelloRossi.getArticoliCarrello().add(articoloCarrello2);
        puntoConsegna= new PuntoConsegna("Le mosse",7);
        prenotazione= new Prenotazione(carrelloRossi,puntoConsegna);
        if(prenotazione.getPacchi().get(0).getArticoli().size()==2) {
            pacco1 = prenotazione.getPacchi().get(0);
            pacco2 = prenotazione.getPacchi().get(1);
        }
        else{
            pacco2 = prenotazione.getPacchi().get(0);
            pacco1 = prenotazione.getPacchi().get(1);
        }
    }

    @Test
    @Order(1)
    void getArticoli() {
        assertEquals(2, pacco1.getArticoli().size());
        assertEquals(3,pacco2.getArticoli().size());
    }

    @Test
    @Order(2)
    void getPuntoVendita() {
        assertEquals(puntoVendita1.getId(), pacco1.getPuntoVendita().getId());
        assertEquals(puntoVendita2.getId(), pacco2.getPuntoVendita().getId());
    }

    @Test
    @Order(3)
    void getPrenotazione() {
        assertEquals(prenotazione,pacco2.getPrenotazione());
    }

    @Test
    @Order(4)
    void getStato() {
        assertEquals(Stato.PresoInCarico,pacco1.getStato());
        pacco1.setStato(Stato.Consegnato);
        assertNotEquals(Stato.PresoInCarico,pacco1.getStato());
    }
}