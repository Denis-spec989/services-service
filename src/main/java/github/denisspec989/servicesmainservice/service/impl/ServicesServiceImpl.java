package github.denisspec989.servicesmainservice.service.impl;

import github.denisspec989.servicesmainservice.domain.ServiceEntity;
import github.denisspec989.servicesmainservice.models.PetrolStationDto;
import github.denisspec989.servicesmainservice.models.ServiceDTO;
import github.denisspec989.servicesmainservice.models.ServiceModelDto;
import github.denisspec989.servicesmainservice.repository.feign.FileServiceRepository;
import github.denisspec989.servicesmainservice.repository.jpa.ServicesRepository;
import github.denisspec989.servicesmainservice.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServicesRepository servicesRepository;
    private final FileServiceRepository fileServiceRepository;

    List<ServiceEntity> fromPetrolStationDtoListToServiceList(List<PetrolStationDto> petrolStationDtoList){
        List<ServiceEntity> priceList = new ArrayList<>();
        for(PetrolStationDto petrolStationDto : petrolStationDtoList){
            for(ServiceModelDto serviceModelDto: petrolStationDto.getServiceModelList()){
                ServiceEntity serviceEntity = new ServiceEntity();
                serviceEntity.setServiceCompanyId(serviceModelDto.getServId());
                serviceEntity.setServiceName(serviceModelDto.getServName());
                serviceEntity.setAzsId(petrolStationDto.getAzsId());
                priceList.add(serviceEntity);
            }
        }
        return priceList;
    }
    List<ServiceDTO> fromServiceEntityListToServiceDTOList(List<ServiceEntity> serviceEntityList){
            List<ServiceDTO> serviceDTOList = new ArrayList<>();
            for(ServiceEntity service:serviceEntityList){
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setServId(service.getServiceCompanyId());
                serviceDTO.setServName(service.getServiceName());
                serviceDTOList.add(serviceDTO);
            }
            return serviceDTOList;
    }
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledGetNewServices() {
        ResponseEntity<List<PetrolStationDto>> responseJson;
        ResponseEntity<List<PetrolStationDto>> responseXML;
        do {
            responseJson = fileServiceRepository.getJsonData("Azs_with_prices_and_services");
            responseXML=fileServiceRepository.getXmlData("Azs_with_prices_and_services");
        } while (!(responseJson.getStatusCode().value()==200&&responseXML.getStatusCode().value()==200));
        List<ServiceEntity> savingList =fromPetrolStationDtoListToServiceList(fileServiceRepository.getJsonData("Azs_with_prices_and_services").getBody());
        List<ServiceEntity> xmlList = fromPetrolStationDtoListToServiceList(fileServiceRepository.getXmlData("Azs_with_prices_and_services").getBody());
        for(ServiceEntity service:xmlList){
                if(savingList.contains(service)){
                    continue;
                }else {
                    savingList.add(service);
                }
        }
        servicesRepository.saveAll(savingList);
    }

    @Override
    @Transactional
    public List<ServiceDTO> getServicesOnAzs(String azsId) {
        return fromServiceEntityListToServiceDTOList(servicesRepository.findAllByAzsId(azsId));
    }
}
