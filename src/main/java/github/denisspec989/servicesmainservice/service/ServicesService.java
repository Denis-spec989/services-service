package github.denisspec989.servicesmainservice.service;

import github.denisspec989.servicesmainservice.models.ServiceDTO;

import java.util.List;

public interface ServicesService {
    void scheduledGetNewServices();
    List<ServiceDTO> getServicesOnAzs(String azsId);
}
