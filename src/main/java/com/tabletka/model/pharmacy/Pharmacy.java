package com.tabletka.model.pharmacy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.order.Order;
import com.tabletka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pharmacy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    private Apothecary apothecary;


}
