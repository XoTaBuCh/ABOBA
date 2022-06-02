package com.tabletka.service;

import com.tabletka.model.apothecary.Apothecary;

import java.util.List;

public interface ApothecaryService {
    void register(Apothecary apothecary);

    void register(String email, String password);


    List<Apothecary> getApothecaries();

    void changeApothecaryStatus(String flag, Long userId);
}
