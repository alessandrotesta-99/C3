package it.unicam.Team151.C3.controller;

import it.unicam.Team151.C3.articoli.Categoria;
import it.unicam.Team151.C3.repositories.CategoriaRepository;
import it.unicam.Team151.C3.repositories.IRepositoryMaster;
import it.unicam.Team151.C3.utenti.Cliente;
import it.unicam.Team151.C3.util.ILoginChecker;
import it.unicam.Team151.C3.util.InterfaceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il caso d'uso 'Visualizza Categorie'
 */
@Service
public class VisualizzaCategorieHandler {

    @Autowired
    IRepositoryMaster repositoryMaster;
    @Autowired
    ILoginChecker loginChecker;
    @Autowired
    InterfaceAdmin admin;

    /**
     * Metodo che restituisce tutte le categorie presenti in C3
     */
    public List<Categoria> getCategorie(Long id){
        if (id == -1 && admin.getLogged() || loginChecker.checkCliente(id) != null) {
            List<Categoria> categorie = new ArrayList<>();
            repositoryMaster.getCategoriaRepository().findAll().forEach(categorie::add);
            return categorie;
        }
        return null;
    }
}
