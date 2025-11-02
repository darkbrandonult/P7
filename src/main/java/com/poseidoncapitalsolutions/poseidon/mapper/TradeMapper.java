package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.TradeDTO;
import com.poseidoncapitalsolutions.poseidon.model.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for Trade entity and TradeDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {
    
    /**
     * Converts Trade entity to TradeDTO.
     * @param trade the trade entity
     * @return the trade DTO
     */
    TradeDTO toDto(Trade trade);
    
    /**
     * Converts TradeDTO to Trade entity.
     * @param tradeDTO the trade DTO
     * @return the trade entity
     */
    Trade toEntity(TradeDTO tradeDTO);
}
