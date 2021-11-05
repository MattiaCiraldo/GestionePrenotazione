package it.mattiaciraldo.spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Citta;
import it.mattiaciraldo.spring.model.Edificio;



@Component
public interface EdificioRepository extends JpaRepository<Edificio, Long> {
	
	@Query("SELECT e FROM Edificio e Where e.nome =:nome")
    public Edificio findEdificioByNome(String nome);
	
	
	/* Sort */
    // Formula: findBy + OrderBy + NomeColonna + Ordinamento(Asc/Desc)
    public List<Edificio> findByOrderByNomeAsc();
    
    public Page<Edificio> findAll(Pageable pageable);
		
}
