package github.denisspec989.servicesmainservice.rest;

import github.denisspec989.servicesmainservice.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
