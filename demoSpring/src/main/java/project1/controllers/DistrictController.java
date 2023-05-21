package project1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project1.model.District;
import project1.model.Person;
import project1.persistence.DatabaseConnector;
import project1.persistence.DistrictService;
import project1.persistence.PersonService;

import java.util.Collection;

@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    DistrictService districtService;

    @Autowired
    PersonService personService;

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ResponseEntity<?> getDistricts(){
//        Collection<District> districts = districtService.getAllDistricts();
//        return new ResponseEntity<Collection<District>>(districts, HttpStatus.OK);
//    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ResponseEntity<?> getMeetingsWithParameters(@RequestParam(value = "title", defaultValue = "", required = false) String title,
//                                                       @RequestParam(value = "sortMode", defaultValue = "", required = false) String sortMode,
//                                                       @RequestParam(value = "description", defaultValue = "", required = false) String description,
//                                                       @RequestParam(value = "participant", defaultValue = "", required = false) String participantLogin) {
//        Participant participant = participantService.findByLogin(participantLogin);
//        if (participant == null && participantLogin.length() > 0) {
//            return new ResponseEntity<String>("A participant with login " + participantLogin + " does not exist.",
//                    HttpStatus.NOT_FOUND);
//        }
//        Collection<Meeting> meetings = meetingService.findMeetings(title, description, participant, sortMode);
//        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
//    }
//http://localhost:8080/district?name=Silver
    //http://localhost:8080/district?name=Golden&sortBy=name
    //http://localhost:8080/district?name=District&sortBy=name
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDistrictsExtended(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                                  @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy){
        Collection<District> districts = districtService.findDistricts(name, sortBy);
        return new ResponseEntity<Collection<District>>(districts, HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneDistrict(@PathVariable("id") long id){
        District district = districtService.getDistrictById(id);
        if (district == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(district,HttpStatus.OK);
        }
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> addOneDistrict(@RequestBody District district){
        long id = district.getId();
        if (districtService.getDistrictById(id) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            districtService.addANewDistrict(district);
            return new ResponseEntity<>(district, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeOneDistrict(@PathVariable("id") long id){
        if (districtService.getDistrictById(id)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            districtService.deleteADistrict(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOneDistrict(@PathVariable("id") long id, @RequestBody District newDistrict){
        District district = districtService.getDistrictById(id);
        if (district == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            newDistrict.setId(id);
            districtService.updateADistrict(newDistrict);
            return new ResponseEntity<>(newDistrict, HttpStatus.OK);
        }
    }

    @RequestMapping(value ="/{id}/people", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPeopleFromOneDistrict(@PathVariable("id") long id){
        District district = districtService.getDistrictById(id);
        if (district == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            Collection<Person> people = district.getPeople();
            return new ResponseEntity<>(people,HttpStatus.OK);

        }
    }

    @RequestMapping(value = "/{id}/people", method = RequestMethod.POST)
    public ResponseEntity<?> addAPersonToADistrict(@PathVariable("id") long id, @RequestBody Person person){
        District district = districtService.getDistrictById(id);
        if (district == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        long personId = person.getId();
        if (personService.getAPersonById(personId) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            district.addAPerson(person);
            districtService.updateADistrict(district);
            Collection<Person> people = district.getPeople();
            return new ResponseEntity<>(people,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}/people/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeAPersonFromDistrict(@PathVariable("id") long id, @PathVariable("login") long personId){
        District district = districtService.getDistrictById(id);
        if (district == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Person person = personService.getAPersonById(personId);
        if (person  == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            district.removeAPerson(person);
            districtService.updateADistrict(district);
            Collection<Person> people = district.getPeople();
            return new ResponseEntity<>(people,HttpStatus.OK);
        }
    }




}
