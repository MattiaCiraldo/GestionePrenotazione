package it.mattiaciraldo.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.mattiaciraldo.spring.model.Postazione;
import it.mattiaciraldo.spring.model.TipoPostazione;
import it.mattiaciraldo.spring.service.EdificioService;
import it.mattiaciraldo.spring.service.PostazioneService;

@RestController
@RequestMapping("/postazionecontroller")
public class PostazioneController {
	@Autowired
	private PostazioneService postazioneService;
	@Autowired
	private EdificioService edificioService;
	
	
	@GetMapping("/postazione-tipo-citta")
	public List<Optional<Postazione>> postazioneTipoCitta(@RequestParam TipoPostazione tipo, String citta) {
		return postazioneService.findPostazioneByTipoCitta(tipo, citta);
		//LINK: http://localhost:8080/postazionecontroller/postazione-tipo-citta?tipo=OPENSPACE&citta=Roma
		//Tipo =  PRIVATO   OPENSPACE   SALA_RIUNIONI
	}
	@GetMapping("/postazioneall")
    public List<Postazione> getPostazioneAll(){
        List<Postazione> listPostazione = postazioneService.myFindAllPostazione();
        return listPostazione;
    }
    
    @GetMapping("/postazioneid/{id}")
    public Optional<Postazione> getPostazioneId(@PathVariable(required = true) Long id){
        return postazioneService.findPostazioneById(id);
    }
    @GetMapping("/postazionesave")
    public void salvaPostazione(@RequestParam String codice, String descrizione, Integer numeroMassimoOccupanti, TipoPostazione tipo, String edificio) {
        postazioneService.myInsertPostazione(codice, descrizione, numeroMassimoOccupanti, tipo, edificioService.myFindEdificioByNome(edificio));
    }
	@GetMapping(value = "/mygetalluserssortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Postazione> myGetAllPostazioneSortByCodice() {
        return postazioneService.myFindAllPostazioneSorted();
    }
	@GetMapping(value = "/mygetalluserspagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Postazione>> myGetAllPostazionePageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<Postazione> list = postazioneService.myFindAllPostazionePageSizeSort(page, size, sort);
      return new ResponseEntity<List<Postazione>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
}
