package com.tabletka.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pharmacy_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyCreationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private Long apothecaryAdminId;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;
}
