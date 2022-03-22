package edu.greenriver.sdev.myspringproject.controllers;

import edu.greenriver.sdev.myspringproject.models.Option;
import edu.greenriver.sdev.myspringproject.models.Package;
import edu.greenriver.sdev.myspringproject.services.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ruslan
 * @version 1.0
 *
 * This class represents REST controller
 */
@RestController
@RequestMapping("detailing")
public class PackageControllerAPI {
    private PackageService service;

    public PackageControllerAPI(PackageService service) {
        this.service = service;
    }

    //------------FIRST END POINT - PACKAGE---------------

    //GET
    @GetMapping("allPackages")
    public ResponseEntity<List<Package>> allPackages() {
        return new ResponseEntity<>(service.allPackages(), HttpStatus.OK);
    }

    @GetMapping("allPackages/{id}")
    public ResponseEntity<Package> packageById(@PathVariable int id) {
        if(!service.packageExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.packageById(id), HttpStatus.OK);
    }

    //POST
    @PostMapping
    public ResponseEntity<Package> addPackage(@RequestBody Package singlePackage) {
        return new ResponseEntity<>(service.savePackage(singlePackage), HttpStatus.CREATED);
    }

    //PUT
    @PutMapping
    public ResponseEntity<Package> editPackage(@RequestBody Package singlePackage) {
        if(!service.packageExists(singlePackage.getPackageId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.savePackage(singlePackage), HttpStatus.NO_CONTENT);
    }

    //DELETE
    @DeleteMapping("package/{id}")
    public ResponseEntity deletePackage(@PathVariable int id) {
        service.deletePackage(service.packageById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //------------SECOND END POINT - OPTION---------------

    //GET
    @GetMapping("package/{id}/options")
    public ResponseEntity<List<Option>> getOptions(@PathVariable int id) {
        return new ResponseEntity<>(service.optionForPackage(id), HttpStatus.OK);
    }

    //POST
    @PostMapping("package/{id}/options")
    public ResponseEntity<Option> addOptionToPackage(@PathVariable int id, @RequestBody Option option) {
        if(!service.packageExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.addOption(option, id), HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("package/{packageId}/options")
    public ResponseEntity<Option> editOption(@PathVariable int packageId, @RequestBody Option option) {
        if(!service.packageExists(packageId) || !service.optionExists(option.getOptionId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(service.addOption(option, packageId), HttpStatus.NO_CONTENT);
    }

    //DELETE
    @DeleteMapping("package/{packageId}/options/{optionId}")
    public ResponseEntity deleteOption(@PathVariable int packageId, @PathVariable int optionId) {
        if(!service.packageExists(packageId) || !service.optionExists(optionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteOption(service.optionById(optionId));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}