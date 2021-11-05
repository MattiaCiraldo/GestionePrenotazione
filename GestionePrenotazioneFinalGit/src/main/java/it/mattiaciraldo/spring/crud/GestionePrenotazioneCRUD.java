package it.mattiaciraldo.spring.crud;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Citta;
import it.mattiaciraldo.spring.model.Edificio;
import it.mattiaciraldo.spring.model.Postazione;
import it.mattiaciraldo.spring.model.Prenotazione;
import it.mattiaciraldo.spring.model.Role;
import it.mattiaciraldo.spring.model.RoleType;
import it.mattiaciraldo.spring.model.TipoPostazione;
import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.repository.CittaRepository;
import it.mattiaciraldo.spring.repository.EdificioRepository;
import it.mattiaciraldo.spring.repository.PostazioneRepository;
import it.mattiaciraldo.spring.repository.PrenotazioneRepository;
import it.mattiaciraldo.spring.repository.RoleRepository;
import it.mattiaciraldo.spring.repository.UserRepository;

@Component
public class GestionePrenotazioneCRUD implements CommandLineRunner {
	
	@Autowired
	CittaRepository cittaRepository;
	
	@Autowired
	EdificioRepository edificioRepository;
	
	@Autowired
	PostazioneRepository postazioneRepository;
	
	@Autowired
	PrenotazioneRepository prenotazioneRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;

	public void popolaDb() {
		Role roleA= new Role(RoleType.ROLE_ADMIN);
		Role roleU=new Role(RoleType.ROLE_USER);
		User u1 = new User("mario126","mario","mario@email.it","password",true);
		User u2 = new User("fantozzi","Michele","mechele@email.it","password",true);
		u1.getRoles().add(roleA);
		u1.getRoles().add(roleU);
		u2.getRoles().add(roleU);
		
		Citta citta = new Citta("Roma");
		Edificio edificio1 = new Edificio("Palazzo", "Via del Corso", citta);
		Edificio edificio2 = new Edificio("Biblioteca", "Via Umberto", citta);
		
		Postazione postazione1= new Postazione("001", "Postazione Palazzo", 4, TipoPostazione.OPENSPACE, edificio1);
		Postazione postazione2= new Postazione("002", "Postazione Riunione", 10, TipoPostazione.SALA_RIUNIONI, edificio1);
		Postazione postazione3= new Postazione("001", "Postazione Lettura", 2, TipoPostazione.PRIVATO, edificio2);
		Postazione postazione4= new Postazione("001", "Postazione Computer", 20, TipoPostazione.OPENSPACE, edificio2);
		
		Prenotazione prenotazione1= new Prenotazione(u1, postazione1, LocalDate.of(2022, 03, 15));
		Prenotazione prenotazione2= new Prenotazione(u1, postazione4, LocalDate.of(2022, 06, 15));
		Prenotazione prenotazione3= new Prenotazione(u2, postazione2, LocalDate.of(2022, 03, 15));
		Prenotazione prenotazione4= new Prenotazione(u2, postazione3, LocalDate.of(2022, 06, 15));

		roleRepository.save(roleA);
		roleRepository.save(roleU);
		userRepository.save(u1);
		userRepository.save(u2);
		cittaRepository.save(citta);
		edificioRepository.save(edificio1);
		edificioRepository.save(edificio2);
		postazioneRepository.save(postazione1);
		postazioneRepository.save(postazione2);
		postazioneRepository.save(postazione3);
		postazioneRepository.save(postazione4);
		prenotazioneRepository.save(prenotazione1);
		prenotazioneRepository.save(prenotazione2);
		prenotazioneRepository.save(prenotazione3);
		prenotazioneRepository.save(prenotazione4);
	}
	
	@Override
	public void run(String... args) throws Exception {
		popolaDb();
		String p = prenotazioneRepository.findById((long)1).toString();
		String u = userRepository.findById((long)1).toString();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		System.out.println("\n\n"+p);
		System.out.println("\n"+u);
		System.out.println("\n\n\n-----------------------------\n");
	}	
}
