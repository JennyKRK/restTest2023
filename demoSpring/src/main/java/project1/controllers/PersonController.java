package project1.controllers;

import java.util.Collection;

import project1.model.Person;
import project1.persistence.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getPeople(){
        Collection<Person> people = personService.getAllPeople();
        return new ResponseEntity<Collection<Person>>(people, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOnePerson(@PathVariable("id") long id){
        Person person = personService.getAPersonById(id);
        if (person == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(person,HttpStatus.OK);
        }
    }

    @RequestMapping(value ="", method = RequestMethod.POST)
    public ResponseEntity<?> addOnePerson(@RequestBody Person person){
        if (personService.getAPersonById(person.getId()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            personService.addAPerson(person);
            return new ResponseEntity<>(person,HttpStatus.CREATED);
        }
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOnePerson(@PathVariable("id")long id){
        if (personService.getAPersonById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            personService.deleteAPerson(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOnePerson(@RequestBody Person newPerson, @PathVariable("id") long id){
        Person person = personService.getAPersonById(id);
        if (person == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        else {
            newPerson.setId(id);
            personService.updateAPerson(newPerson);
            return new ResponseEntity<>(newPerson,HttpStatus.OK);
        }
    }
}
