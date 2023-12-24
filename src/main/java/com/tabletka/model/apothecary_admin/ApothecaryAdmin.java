package com.tabletka.model.apothecary_admin;

import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apothecary_admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApothecaryAdmin extends User {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "apothecaryAdmin", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies;
}
