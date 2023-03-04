package github.denisspec989.servicesmainservice.repository.jpa;

import github.denisspec989.servicesmainservice.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServicesRepository extends JpaRepository<ServiceEntity, UUID> {
    List<ServiceEntity>  findAllByAzsId(String azsId);
}
