package github.denisspec989.servicesmainservice.service.impl;

import github.denisspec989.servicesmainservice.domain.ServiceEntity;
import github.denisspec989.servicesmainservice.models.PetrolStationDto;
import github.denisspec989.servicesmainservice.models.ServiceModelDto;
import github.denisspec989.servicesmainservice.repository.feign.FileServiceRepository;
import github.denisspec989.servicesmainservice.repository.jpa.ServicesRepository;
import github.denisspec989.servicesmainservice.service.ServicesService;
import lombok.RequiredArgsConstructor;
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
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledGetNewServices() {
        List<ServiceEntity> savingList =fromPetrolStationDtoListToServiceList(fileServiceRepository.getJsonData("Azs_with_prices_and_services"));
        System.out.println(savingList.size());
        List<ServiceEntity> xmlList = fromPetrolStationDtoListToServiceList(fileServiceRepository.getXmlData("Azs_with_prices_and_services"));
        for(ServiceEntity service:xmlList){
                if(savingList.contains(service)){
                    continue;
                }else {
                    savingList.add(service);
                }
        }
        System.out.println(savingList.size());
        servicesRepository.saveAll(savingList);
    }
}
