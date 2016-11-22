package com.spx.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "ROOMS")
public class RoomEntity {

    private Long id;
    private String name;
    private Long amount;

    private HousingEntity housing;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOMS_SEQ")
    @SequenceGenerator(name = "ROOMS_SEQ", sequenceName = "ROOMS_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public HousingEntity getHousing() {
        return housing;
    }

    public void setHousing(HousingEntity housing) {
        this.housing = housing;
    }


    @Basic
    @Column(name = "AMOUNT", nullable = false)
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
