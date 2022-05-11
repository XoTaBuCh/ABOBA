package com.tabletka.model.pharmacy;

import com.tabletka.model.order.Order;
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

    //@OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Order> orders = new ArrayList<>();

}
