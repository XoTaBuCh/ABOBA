package com.tabletka.model.apothecary;

import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "apothecary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apothecary extends User {
    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;
}
