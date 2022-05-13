package com.tabletka.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tabletka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicine")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String fabricator;

    @Enumerated(value = EnumType.STRING)
    private MedicinesType medicinesType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "medicine", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
