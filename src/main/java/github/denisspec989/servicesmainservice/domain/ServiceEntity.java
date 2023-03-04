package github.denisspec989.servicesmainservice.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "services")
@Getter
@Setter
@DynamicUpdate
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID service_id;
    private String serviceCompanyId;
    private String serviceName;
    private String azsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceEntity)) return false;
        ServiceEntity that = (ServiceEntity) o;
        return Objects.equals(getServiceCompanyId(), that.getServiceCompanyId()) && Objects.equals(getAzsId(), that.getAzsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceCompanyId(), getAzsId());
    }
}
