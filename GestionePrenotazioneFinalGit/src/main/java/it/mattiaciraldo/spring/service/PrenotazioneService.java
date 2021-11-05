package it.mattiaciraldo.spring.service;

import java.time.LocalDate;
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
import it.mattiaciraldo.spring.model.Postazione;
import it.mattiaciraldo.spring.model.Prenotazione;
import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	@Autowired
	PrenotazioneRepository prenotazioneRepository;

	public List<Prenotazione> myFindAllPrenotazione() {
		return prenotazioneRepository.findAll();
	}

	public Optional<Prenotazione>  myFindPrenotazioneById(long id) {
		return prenotazioneRepository.findById(id);
	}

	public void myInsertPrenotazione(User user, Postazione postazione, LocalDate dataPrenotata) {
		prenotazioneRepository.save(new Prenotazione(user, postazione, dataPrenotata));
	}

	public boolean diffGiorni(LocalDate dataPrenotata) {
		return LocalDate.now().isBefore(dataPrenotata.minusDays(2l));

	}

	public boolean listaPrenUserData(long id, LocalDate date) {
		List<Prenotazione> lista = prenotazioneRepository.findPrenotazioneByUserData(id, date);
		if (lista.isEmpty()) {
			return true;

		} else
			return false;

	}
	 // Ordinamento
    public List<Prenotazione> myFindAllPrenotazioneSorted() {
        return prenotazioneRepository.findByOrderByUserAsc();
    }
 // Paginazione e Ordinamento
    public List<Prenotazione> myFindAllPrenotazionePageSizeSort(Integer page, Integer size, String sort) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
        Page<Prenotazione> pagedResult = prenotazioneRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Prenotazione>();
        }
    }
}
