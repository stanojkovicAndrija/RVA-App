package rva.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.jpa.Projekat;
import rva.repositories.ProjekatRepository;

	@CrossOrigin
	@RestController
	public class ProjekatRestController{

		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private ProjekatRepository projekatRepository;
		
		@GetMapping("projekat")
		public Collection<Projekat> getProjekat() {
			return projekatRepository.findAll();
		}
		
		@GetMapping("projekat/{id}")
		public Projekat getProjekat(@PathVariable("id") Integer id) {
			return projekatRepository.getOne(id);
		}
		@GetMapping("projekat/projekatNaziv/{naziv}")
		public Collection<Projekat> getProjekatByNaziv(@PathVariable("naziv") String naziv)
		{
			return projekatRepository.findByNazivContainingIgnoreCase(naziv);
		}
		@PostMapping("projekat")
		public ResponseEntity<Projekat>insertProjekat(@RequestBody Projekat projekat)
		{
			if(!projekatRepository.existsById(projekat.getId())) 
			{
				projekatRepository.save(projekat);
				return new ResponseEntity<Projekat>(HttpStatus.OK);
			}
			return new ResponseEntity<Projekat>(HttpStatus.CONFLICT);
		}
		@PutMapping("projekat")
		public ResponseEntity<Projekat>updateProjekat(@RequestBody Projekat projekat)
		{
			if(!projekatRepository.existsById(projekat.getId()))
			{
				return new ResponseEntity<Projekat>(HttpStatus.NO_CONTENT);
			}
			projekatRepository.save(projekat);
			return new ResponseEntity<Projekat>(HttpStatus.OK);
		}
		@DeleteMapping("projekat/{id}")
		public ResponseEntity<Projekat> deleteProjekat(@PathVariable("id") Integer id)
		{
			if(!projekatRepository.existsById(id))
			{
				return new ResponseEntity<Projekat>(HttpStatus.NO_CONTENT);
			}
			projekatRepository.deleteById(id);
			if (id==-100)
			{
				jdbcTemplate.execute(
							"INSERT INTO projekat(id,naziv,oznaka,opis) "
							+ "VALUES (-100,'TestNaziv','TestOznaka','TestOpis')"
						);
			}
			return new ResponseEntity<Projekat>(HttpStatus.OK);
		}
	}

