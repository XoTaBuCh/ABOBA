package com.tabletka.model.admin;

import com.tabletka.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

}
