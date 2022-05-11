package com.tabletka.model.apothecary;
import com.tabletka.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apothecary")
@Getter
@Setter
@NoArgsConstructor
public class Apothecary extends User {

}
