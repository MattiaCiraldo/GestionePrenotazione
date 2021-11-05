package it.mattiaciraldo.spring.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Citta;


@Component
public interface CittaRepository extends JpaRepository<Citta, Long> {
	
	@Query("SELECT c FROM Citta c Where c.nome =:nome")
    public Citta findCittaByNome(String nome);
	
	/* Sort */
    // Formula: findBy + OrderBy + NomeColonna + Ordinamento(Asc/Desc)
    public List<Citta> findByOrderByNomeAsc();
    
    public Page<Citta> findAll(Pageable pageable);
		
}
