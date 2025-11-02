package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.BidListDTO;
import com.poseidoncapitalsolutions.poseidon.model.BidList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for BidList entity and BidListDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidListMapper {
    
    /**
     * Converts BidList entity to BidListDTO.
     * @param bidList the bid list entity
     * @return the bid list DTO
     */
    BidListDTO toDto(BidList bidList);
    
    /**
     * Converts BidListDTO to BidList entity.
     * @param bidListDTO the bid list DTO
     * @return the bid list entity
     */
    BidList toEntity(BidListDTO bidListDTO);
}
