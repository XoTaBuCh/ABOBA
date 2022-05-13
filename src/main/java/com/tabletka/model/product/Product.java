package com.tabletka.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.order.Order;
import com.tabletka.model.pharmacy.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;
    private int amount;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;
    @ManyToOne(fetch = FetchType.EAGER)
    private Medicine medicine;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> products = new ArrayList<>();
}
