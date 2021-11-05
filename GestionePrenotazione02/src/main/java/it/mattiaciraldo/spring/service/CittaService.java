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
import it.mattiaciraldo.spring.repository.CittaRepository;

@Service
public class CittaService {
	@Autowired
	CittaRepository cittaRepository;
	
	public List<Citta> myFindAllCitta() {
        return cittaRepository.findAll();
    }
    
    public Citta myFindCittaByNome(String nome) {
        return cittaRepository.findCittaByNome(nome);
    }
    
    public void myInsertCitta(String nome) {
        cittaRepository.save(new Citta(nome));
    }
 // Ordinamento
    public List<Citta> myFindAllCittaSorted() {
        return cittaRepository.findByOrderByNomeAsc();
    }
 // Paginazione e Ordinamento
    public List<Citta> myFindAllUsersPageSizeSort(Integer page, Integer size, String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
        Page<Citta> pagedResult = cittaRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Citta>();
        }
    }
}
