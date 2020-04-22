package edu.puj.patrones.cliente.mapper;

import edu.puj.patrones.cliente.domain.Provider;
import edu.puj.patrones.cliente.dto.ProviderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProviderMapper {
    Provider toEntity(ProviderDTO dto);

    ProviderDTO toDto(Provider entity);
}
