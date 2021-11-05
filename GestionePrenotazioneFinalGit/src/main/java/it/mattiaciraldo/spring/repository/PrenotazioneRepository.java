package it.mattiaciraldo.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Prenotazione;

@Component
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	@Query("SELECT pre FROM Prenotazione pre WHERE pre.user.id=:id AND pre.dataPrenotata =:dataPrenotata")
	public  List<Prenotazione> findPrenotazioneByUserData(long id, LocalDate dataPrenotata);

    public List<Prenotazione> findByOrderByUserAsc();
    
    public Page<Prenotazione> findAll(Pageable pageable);
		
}
