package edu.greenriver.sdev.myspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * @author Ruslan Bessarab
 * @version 1.0
 * This class represents option for a package
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionId;

    private String name;
    private int points;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private Package singlePackage;
}
