package it.mattiaciraldo.spring.controller;

import java.util.List;

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
import it.mattiaciraldo.spring.model.Edificio;
import it.mattiaciraldo.spring.service.CittaService;
import it.mattiaciraldo.spring.service.EdificioService;

@RestController
@RequestMapping("/edificiocontroller")
public class EdificioController {
	@Autowired
	private EdificioService edificioService;
	@Autowired
	private CittaService cittaService;

	@GetMapping("/edificioall")
	public List<Edificio> getEdificioAll() {
		List<Edificio> lstEd = edificioService.myFindAllUsers();
		return lstEd;
	}

	@GetMapping("/edificiobynome/{nome}")
	public Edificio getEdificiByNome(@PathVariable(required = true) String nome) {
		Edificio e = edificioService.myFindEdificioByNome(nome);
		return e;
	}
	@GetMapping("/edificiosave")
	public void SalvaEdificio(@RequestParam String nome, String indirizzo, String citta) {
		 edificioService.myInsertEdificio(nome, indirizzo, cittaService.myFindCittaByNome(citta));
		
	}
	@GetMapping(value = "/mygetalluserssortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Edificio> myGetAllCittaSortByName() {
        return edificioService.myFindAllCittaSorted();
    }
	@GetMapping(value = "/mygetalluserspagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Edificio>> myGetAllUserPageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<Edificio> list = edificioService.myFindAllUsersPageSizeSort(page, size, sort);
      return new ResponseEntity<List<Edificio>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
}
