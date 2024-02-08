package com.app.weatherGPT.model;    /*
 *created by WerWolfe on
 */


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cities")
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String state;
    @Column(name = "post_code")
    private String postCode;

}
