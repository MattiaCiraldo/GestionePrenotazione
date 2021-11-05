package it.mattiaciraldo.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.mattiaciraldo.spring.model.Citta;
import it.mattiaciraldo.spring.model.Prenotazione;
import it.mattiaciraldo.spring.service.PostazioneService;
import it.mattiaciraldo.spring.service.PrenotazioneService;
import it.mattiaciraldo.spring.service.UserService;

@RestController
@RequestMapping("/prenotazionecontroller")
public class PrenotazioneController {
	@Autowired
	private PrenotazioneService prenotazioneService;

	@Autowired
	private UserService userService;

	@Autowired
	private PostazioneService postazioneService;

	@GetMapping("/creaprenotazione")
	public String creaPrenotazione(@RequestParam long userId, long postazioneId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataPrenotata) {
		if (prenotazioneService.diffGiorni(dataPrenotata)
				&& prenotazioneService.listaPrenUserData(userId, dataPrenotata)) {
			prenotazioneService.myInsertPrenotazione(userService.myFindUserById(userId),
					postazioneService.myFindPostazioneById(postazioneId), dataPrenotata);
			return "<h2><i>Prenotazione inserita correttamente!</i></h2><br><br><br><button><a href=\"http://localhost:8080/\">Nuova Prenotazione</a></button>";
		} else {
			System.out.println("Errore");
			return "<h2>ERRORE DATA NON DISPONIBILE</h2><br><br><a href=\"http://localhost:8080/\"><i>Scegliere una nuova data</i></a>";

		}
	}

	@GetMapping("/getprenotazioneall")
	public List<Prenotazione> getAllPrenotazione() {
		List<Prenotazione> listPre= prenotazioneService.myFindAllPrenotazione();
		return listPre;
	}

	@GetMapping("/getprenotazionebyid/{id}")
	public Optional<Prenotazione> getPrenotazioneById(@PathVariable(required = true) long id) {
		Optional<Prenotazione> p = prenotazioneService.myFindPrenotazioneById(id);
		return p;
	}
	@GetMapping(value = "/mygetalluserssortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prenotazione> myGetAllCittaSortByName() {
        return prenotazioneService.myFindAllPrenotazioneSorted();
    }
	@GetMapping(value = "/mygetalluserspagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Prenotazione>> myGetAllUserPageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<Prenotazione> list = prenotazioneService.myFindAllPrenotazionePageSizeSort(page, size, sort);
      return new ResponseEntity<List<Prenotazione>>(list, new HttpHeaders(), HttpStatus.OK); 
    }

}
