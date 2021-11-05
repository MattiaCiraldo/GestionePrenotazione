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
import it.mattiaciraldo.spring.repository.EdificioRepository;

@Service
public class EdificioService {
	@Autowired
	EdificioRepository edificioRepository;

	public List<Edificio> myFindAllUsers() {
		return edificioRepository.findAll();
	}

	public Edificio myFindEdificioByNome(String nome) {
		return edificioRepository.findEdificioByNome(nome);
	}

	public void myInsertEdificio(String nome, String indirizzo, Citta citta) {
		edificioRepository.save(new Edificio(nome, indirizzo, citta));
	}

	// Ordinamento
	public List<Edificio> myFindAllCittaSorted() {
		return edificioRepository.findByOrderByNomeAsc();
	}

	// Paginazione e Ordinamento
	public List<Edificio> myFindAllUsersPageSizeSort(Integer page, Integer size, String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sort));
		Page<Edificio> pagedResult = edificioRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Edificio>();
		}
	}
}