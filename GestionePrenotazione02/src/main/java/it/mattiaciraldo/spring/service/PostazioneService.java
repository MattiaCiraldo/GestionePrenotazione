package it.mattiaciraldo.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.mattiaciraldo.spring.model.Citta;
import it.mattiaciraldo.spring.model.Edificio;
import it.mattiaciraldo.spring.model.Postazione;
import it.mattiaciraldo.spring.model.TipoPostazione;
import it.mattiaciraldo.spring.repository.PostazioneRepository;

@Service
public class PostazioneService {
	@Autowired
	PostazioneRepository postazioneRepository;
	
	public List<Postazione> myFindAllPostazione() {
        return postazioneRepository.findAll();
    }
    
    public Postazione myFindPostazioneById(long id) {
        return postazioneRepository.getById(id);
    }
    
    public List<Optional<Postazione>> findPostazioneByTipoCitta(TipoPostazione tipo, String citta){
    	return postazioneRepository.findPostazioneByTipoCitta(tipo, citta);    	
    }
    
    public void myInsertPostazione(String codice, String descrizione, Integer numeroMassimoOccupanti, TipoPostazione tipo,
			Edificio edificio) {
    	postazioneRepository.save(new Postazione(codice, descrizione,numeroMassimoOccupanti, tipo,
    		edificio));
    }
    public Optional<Postazione> findPostazioneById(Long id) {
        return postazioneRepository.findById(id);
    }
    // Ordinamento
    public List<Postazione> myFindAllPostazioneSorted() {
        return postazioneRepository.findByOrderByCodiceAsc();
    }
 // Paginazione e Ordinamento
    public List<Postazione> myFindAllPostazionePageSizeSort(Integer page, Integer size, String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
        Page<Postazione> pagedResult = postazioneRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Postazione>();
        }
    }
}
