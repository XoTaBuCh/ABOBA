package com.tabletka.model.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "registry_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApothecaryRegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;
}
