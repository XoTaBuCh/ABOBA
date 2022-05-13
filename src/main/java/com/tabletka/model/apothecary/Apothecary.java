package com.tabletka.model.apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apothecary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apothecary extends User {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "apothecary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pharmacy> pharmacies;
}
