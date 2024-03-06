package com.app.weatherGPT.model.location;    /*
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
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @Column(name = "post_code")
    private String postCode;

    public City(String name, Region region) {
        this.name = name;
        this.region = region;
    }
}
