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

import rva.jpa.Grupa;
import rva.repositories.GrupaRepository;

	@CrossOrigin
	@RestController
	public class GrupaRestController{

		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private GrupaRepository grupaRepository;
		
		@GetMapping("grupa")
		public Collection<Grupa> getGrupa() {
			return grupaRepository.findAll();
		}
		
		@GetMapping("grupa/{id}")
		public Grupa getGrupa(@PathVariable("id") Integer id) {
			return grupaRepository.getOne(id);
		}
		@GetMapping("grupa/grupaOznaka/{oznaka}")
		public Collection<Grupa> getGrupaByNaziv(@PathVariable("oznaka") String oznaka)
		{
			return grupaRepository.findByOznakaContainingIgnoreCase(oznaka);
		}
		@PostMapping("grupa")
		public ResponseEntity<Grupa>insertGrupa(@RequestBody Grupa grupa)
		{
			if(!grupaRepository.existsById(grupa.getId())) 
			{
				grupaRepository.save(grupa);
				return new ResponseEntity<Grupa>(HttpStatus.OK);
			}
			return new ResponseEntity<Grupa>(HttpStatus.CONFLICT);
		}
		@PutMapping("grupa")
		public ResponseEntity<Grupa>updateGrupa(@RequestBody Grupa grupa)
		{
			if(!grupaRepository.existsById(grupa.getId()))
			{
				return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
			}
			grupaRepository.save(grupa);
			return new ResponseEntity<Grupa>(HttpStatus.OK);
		}
		@DeleteMapping("grupa/{id}")
		public ResponseEntity<Grupa> deleteGrupa(@PathVariable("id") Integer id)
		{
			if(!grupaRepository.existsById(id))
			{
				return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
			}
			grupaRepository.deleteById(id);
			if (id==-100)
			{
				jdbcTemplate.execute(
							"INSERT INTO grupa (id,oznaka,smer) "
							+ "VALUES (-100,'TestOznaka',1)"
						);
			}
			return new ResponseEntity<Grupa>(HttpStatus.OK);
		}
	}

