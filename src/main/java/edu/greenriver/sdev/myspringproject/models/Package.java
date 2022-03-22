package edu.greenriver.sdev.myspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ruslan Bessarab
 * @version 1.0
 *
 * This class represents a data model
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Package {
    //id for each package
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageId;

    private String package_name;
    private String car_model;
    private double price;
    private boolean additional_options;
    private LocalDate date;
    private int points;

    @ToString.Exclude
    @OneToMany(mappedBy = "singlePackage")
    @JsonIgnore
    private List<Option> options;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "userId")
    private User customer;

    public void addOption(Option option) {
        options.add(option);
    }
}
