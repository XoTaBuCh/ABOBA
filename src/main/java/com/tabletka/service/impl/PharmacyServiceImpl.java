package com.tabletka.service.impl;

import com.tabletka.dto.ForecastDto;
import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecaryAdmin.ApothecaryAdmin;
import com.tabletka.model.order.Order;
import com.tabletka.model.order.OrderStatus;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.model.requests.PharmacyCreationRequest;
import com.tabletka.model.requests.RequestStatus;
import com.tabletka.repository.ApothecaryAdminRepository;
import com.tabletka.repository.MedicineRepository;
import com.tabletka.repository.PharmacyCreatingRequestRepository;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.repository.ProductRepository;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.PharmacyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final AuthContextHandler authContextHandler;
    private final ProductRepository productRepository;
    private final MedicineRepository medicineRepository;
    private final PharmacyCreatingRequestRepository pharmacyCreatingRequestRepository;
    private final ApothecaryAdminRepository apothecaryAdminRepository;


    public List<Pharmacy> getPharmaciesForAdminApothecary() throws UserIsNotLoggedInException {
        ApothecaryAdmin apothecaryAdmin = ((ApothecaryAdmin) authContextHandler.getLoggedInUser());
        return pharmacyRepository.findAllByApothecaryAdmin(apothecaryAdmin);
    }

    @Override
    public void addPharmacy(Pharmacy pharmacy) throws UserIsNotLoggedInException {
        ApothecaryAdmin apothecary = ((ApothecaryAdmin) authContextHandler.getLoggedInUser());
        PharmacyCreationRequest request = new PharmacyCreationRequest();

        request.setAddress(pharmacy.getAddress());
        request.setName(pharmacy.getName());
        request.setRequestStatus(RequestStatus.PENDING);
        request.setApothecaryAdminId(apothecary.getId());
        pharmacyCreatingRequestRepository.save(request);
    }

    @Override
    public void addPharmacy(String name, String address, Long apothecaryId) {
        ApothecaryAdmin apothecary = apothecaryAdminRepository.findApothecaryAdminById(apothecaryId);
        Pharmacy pharmacy = new Pharmacy();

        pharmacy.setAddress(address);
        pharmacy.setName(name);
        pharmacy.setApothecaryAdmin(apothecary);
        pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy getPharmacyById(Long id) {
        return pharmacyRepository.findPharmacyById(id);
    }

    @Override
    public void changeProductInfo(Long id, Long amount, Double price) {
        Product product = productRepository.findProductById(id);
        product.setAmount(amount);
        product.setPrice(price);

        productRepository.save(product);
    }

    @Override
    public void addProduct(Long pharmacyId, Long medicineId, Long amount, Double price) {
        Product product = new Product();
        product.setPurchasedProducts(0L);
        product.setAmount(amount);
        product.setPrice(price);
        product.setPharmacy(pharmacyRepository.findPharmacyById(pharmacyId));
        product.setMedicine(medicineRepository.findMedicineById(medicineId));

        productRepository.save(product);
    }

    @Override
    public List<Order> getPharmacyOrders(Long phId) {
        List<Order> orders = pharmacyRepository.findPharmacyById(phId).getOrders();
        List<Order> activeOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.ACTIVE) {
                activeOrders.add(order);
            }
        }

        if (activeOrders.isEmpty()) return null;
        return activeOrders;
    }


    @Override
    public void changePharmacyNameAddress(String name, String address, Long pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyId);
        pharmacy.setName(name);
        pharmacy.setAddress(address);
        pharmacyRepository.save(pharmacy);
    }

    @Override
    public List<Pharmacy> getPharmacies() {
        return pharmacyRepository.findAll();
    }

    @Override
    public List<ForecastDto> getForecast(Long pId) {
        List<Product> products = pharmacyRepository.findPharmacyById(pId).getProducts();

        return products.stream().map(product -> {
            List<Long> forecast = new ArrayList<>();
            for(long index = 0L; index < 12L; index++) {
                forecast.add(Math.max(0, product.getPurchasedProducts() + getRandomValue()));
            }
            ForecastDto forecastDto = new ForecastDto();
            forecastDto.setForecast(forecast);
            forecastDto.setProduct(product);

            return forecastDto;
        }).collect(Collectors.toList());
    }

    private int getRandomValue() {
        Random random = new Random();
        int value = random.nextInt() % 10;
        return value - 5;
    }

}
