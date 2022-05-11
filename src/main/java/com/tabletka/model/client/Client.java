package com.tabletka.model.client;
import com.tabletka.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User{

}
