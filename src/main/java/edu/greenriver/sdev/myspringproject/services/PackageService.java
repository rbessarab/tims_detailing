package edu.greenriver.sdev.myspringproject.services;

import edu.greenriver.sdev.myspringproject.db.IOptionRepository;
import edu.greenriver.sdev.myspringproject.db.IPackageRepository;
import edu.greenriver.sdev.myspringproject.models.Option;
import edu.greenriver.sdev.myspringproject.models.Package;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


/**
 * @author Ruslan Bessarab
 * @version 1.0
 *
 * This class represents a service class
 */
@Service
public class PackageService {
    private IPackageRepository repo;
    private IOptionRepository optionRepository;

    public PackageService(IPackageRepository repo, IOptionRepository optionRepository) {
        this.repo = repo;
        this.optionRepository = optionRepository;
    }

    //all data records
    public List<Package> allPackages() {
        return repo.findAll();
    }

    //data record by id
    public Package packageById(int id) {
        return repo.findById(id).orElse(null);
    }

    //return the random package (later I'll do recent 3)
    public Package random() {
        List<Package> allPackages = repo.findAll();
        Random random = new Random();
        return allPackages.get(random.nextInt(allPackages.size()));
    }

    //save package
    public Package savePackage(Package single) {
        return repo.save(single);
    }

    //delete package
    public void deletePackage(Package single) {
        repo.delete(single);
    }

    //check if package exists
    public boolean packageExists(int id) {
        return packageById(id) != null;
    }

    //check if option exists
    public boolean optionExists(int id) {
        return optionRepository.findById(id).orElse(null) != null;
    }

    public List<Option> optionForPackage(int packageId) {
        if(repo.findById(packageId).isEmpty()) {
            throw new NoSuchElementException("Missing package");
        }
        return repo.findById(packageId).get().getOptions();
    }

    public Option addOption(Option option, int id) {
        optionRepository.save(option);
        packageById(id).addOption(option);
        option.setSinglePackage(packageById(id));
        return optionRepository.save(option);
    }

    public Option optionById(int id) {
        return optionRepository.findById(id).orElse(null);
    }

    public void deleteOption(Option option) {
        optionRepository.delete(option);
    }
}