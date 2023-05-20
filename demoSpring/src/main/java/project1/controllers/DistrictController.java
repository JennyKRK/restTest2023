package project1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project1.model.District;
import project1.persistence.DatabaseConnector;
import project1.persistence.DistrictService;

import java.util.Collection;

@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    DistrictService districtService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDistricts(){
        Collection<District> districts = districtService.getAllDistricts();
        return new ResponseEntity<Collection<District>>(districts, HttpStatus.OK);
    }


}
