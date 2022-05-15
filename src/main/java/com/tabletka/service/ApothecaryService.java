package com.tabletka.service;

import com.tabletka.model.apothecary.Apothecary;

public interface ApothecaryService {
    void register(Apothecary apothecary);

    void register(String email, String password);
}
