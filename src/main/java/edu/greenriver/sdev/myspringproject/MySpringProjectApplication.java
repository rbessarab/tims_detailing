package edu.greenriver.sdev.myspringproject;

import edu.greenriver.sdev.myspringproject.db.IAuthorityRepository;
import edu.greenriver.sdev.myspringproject.db.IOptionRepository;
import edu.greenriver.sdev.myspringproject.db.IPackageRepository;
import edu.greenriver.sdev.myspringproject.db.IUserRepository;
import edu.greenriver.sdev.myspringproject.models.Authority;
import edu.greenriver.sdev.myspringproject.models.Option;
import edu.greenriver.sdev.myspringproject.models.Package;
import edu.greenriver.sdev.myspringproject.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MySpringProjectApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MySpringProjectApplication.class, args);
        IPackageRepository repo = context.getBean(IPackageRepository.class);
        IOptionRepository optionRepository = context.getBean(IOptionRepository.class);
        IUserRepository userRepository = context.getBean(IUserRepository.class);

        BCryptPasswordEncoder encoder = context.getBean(BCryptPasswordEncoder.class);

        //create few Packages
        Package silver = Package.builder().package_name("Silver").additional_options(true)
                .date(LocalDate.now()).points(100).price(60.00).car_model("Honda Accord").build();

        Package bronze = Package.builder().package_name("Bronze").additional_options(false)
                .date(LocalDate.now()).points(200).price(100.00).car_model("Acura MDX").build();

        Package gold = Package.builder().package_name("Gold").additional_options(false)
                .date(LocalDate.now()).points(300).price(170.00).car_model("Porsche 911").build();

        //create few Option objects
        Option underCarCleaning = Option.builder()
                .name("Clean under car")
                .points(10)
                .build();

        Option interiorSteaming = Option.builder()
                .name("Steaming inside the car")
                .points(20)
                .build();

        //save them
        repo.save(silver);
        repo.save(bronze);
        repo.save(gold);
        optionRepository.save(underCarCleaning);
        optionRepository.save(interiorSteaming);

        //connecting both sides of relationship
        silver.setOptions(new ArrayList<>());
        List<Option> options = silver.getOptions();
        options.add(underCarCleaning);
        options.add(interiorSteaming);
        underCarCleaning.setSinglePackage(silver);
        interiorSteaming.setSinglePackage(silver);

        //save
        repo.save(silver);
        optionRepository.save(underCarCleaning);
        optionRepository.save(interiorSteaming);

        //ADMIN USER
        User admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .build();

        Authority adminRole = new Authority();
        adminRole.setRole("ROLE_ADMIN");
        adminRole.setUser(admin);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(adminRole);
        admin.setAuthorities(authorities);

        userRepository.save(admin);

        System.out.println("User name: admin");
        System.out.println("Password: password");
    }
}