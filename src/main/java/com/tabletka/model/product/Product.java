package com.tabletka.model.product;

import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.order.OrderItem;
import com.tabletka.model.pharmacy.Pharmacy;
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
    private Long amount;
    private Long purchasedProducts;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;
    @ManyToOne(fetch = FetchType.EAGER)
    private Medicine medicine;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orders = new ArrayList<>();
}
