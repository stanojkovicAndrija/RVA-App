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
import rva.jpa.Smer;
import rva.jpa.Student;
import rva.repositories.GrupaRepository;
import rva.repositories.StudentRepository;

	@CrossOrigin
	@RestController
	public class StudentRestController {
	
		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		@Autowired
		private StudentRepository studentRepository;
		@Autowired
		private GrupaRepository grupaRepository;

		
		@GetMapping("student")
		public Collection<Student> getStudenti() {
			return studentRepository.findAll();
		}
		
		@GetMapping("student/{id}")
		public Student getStudent(@PathVariable("id") Integer id) {
			return studentRepository.getOne(id);
		}
		@GetMapping("student/studentIme/{ime}")
		public Collection<Student> getStudentByIme(@PathVariable("ime") String ime)
		{
			return studentRepository.findByImeContainingIgnoreCase(ime);
		}
		@GetMapping("student/grupa/{id}")
		public Collection<Student> getStudentByGrupa(@PathVariable("id") Integer id){
			 
			Grupa g = this.grupaRepository.getOne(id);
			return studentRepository.findByGrupa(g);
		}
		@PostMapping("student")
		public ResponseEntity<Student> InsertStudent(@RequestBody Student student) {
			
			if(!studentRepository.existsById(student.getId()))
			{
				studentRepository.save(student);
				return new ResponseEntity<Student>(HttpStatus.OK);
			}
			return new ResponseEntity<Student>(HttpStatus.CONFLICT);
		}
		@PutMapping("student")
		public ResponseEntity<Student>updateStudent(@RequestBody Student student)
		{
			if(!studentRepository.existsById(student.getId()))
			{
				return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
			}
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		@DeleteMapping("student/{id}")
		public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id)
		{
			if(!studentRepository.existsById(id))
			{
				return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
			}
			studentRepository.deleteById(id);
			if (id==-100)
			{
				jdbcTemplate.execute(
							"insert into student (id,ime,prezime,broj_indeksa,grupa,projekat) "
							+ "values (-100,'TestIme','TestPrez','TestBI',1,1)"
						);
			}
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		
	}
