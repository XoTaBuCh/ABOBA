package com.tabletka.model.pharmacy;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.apothecaryAdmin.ApothecaryAdmin;
import com.tabletka.model.order.Order;
import com.tabletka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private String pharmacyChain;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    private ApothecaryAdmin apothecaryAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Apothecary> apothecaries = new ArrayList<>();
}
