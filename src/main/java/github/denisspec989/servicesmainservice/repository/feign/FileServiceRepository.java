package github.denisspec989.servicesmainservice.repository.feign;

import github.denisspec989.servicesmainservice.models.PetrolStationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "smp",url ="http://localhost:8087")
public interface FileServiceRepository {
    @PostMapping(value = "/api/v1/files/json")
    List<PetrolStationDto> getJsonData(@RequestParam("fileName") String fileName);
    @PostMapping(value = "/api/v1/files/xml")
    List<PetrolStationDto> getXmlData(@RequestParam("fileName") String fileName);
}
