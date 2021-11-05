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
import it.mattiaciraldo.spring.service.CittaService;

@RestController
@RequestMapping("/cittacontroller")
public class CittaController {
	@Autowired
	private CittaService cittaService;

	@GetMapping("/cittaall")
	public List<Citta> getCittaAll() {
		List<Citta> lstcitta = cittaService.myFindAllCitta();
		return lstcitta;
	}

	@GetMapping("/cittabynome/{nome}")
	public Citta getCittaByNome(@PathVariable(required = true) String nome) {
		Citta c = cittaService.myFindCittaByNome(nome);
		return c;

	}
	@GetMapping("/cittasave")
	public void saveCitta(@RequestParam String nome)  {
		cittaService.myInsertCitta(nome);
	}
	@GetMapping(value = "/mygetalluserssortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Citta> myGetAllUserSortByUsername() {
        return cittaService.myFindAllCittaSorted();
    }
	@GetMapping(value = "/mygetalluserspagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Citta>> myGetAllUserPageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<Citta> list = cittaService.myFindAllUsersPageSizeSort(page, size, sort);
      return new ResponseEntity<List<Citta>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
}
