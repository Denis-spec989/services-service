package github.denisspec989.servicesmainservice.rest;

import github.denisspec989.servicesmainservice.models.ServiceDTO;
import github.denisspec989.servicesmainservice.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServicesService servicesService;

    @PostMapping("/load")
    public ResponseEntity manuallyLoadDataFromFileHandlerService(){
        servicesService.scheduledGetNewServices();
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/list")
    List<ServiceDTO> getServicesOnAzs(@RequestParam("azsId") String azsId){
        return servicesService.getServicesOnAzs(azsId);
    }

}
