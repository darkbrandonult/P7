package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.model.CurvePoint;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for CurvePoint entity and CurvePointDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurvePointMapper {
    
    /**
     * Converts CurvePoint entity to CurvePointDTO.
     * @param curvePoint the curve point entity
     * @return the curve point DTO
     */
    CurvePointDTO toDto(CurvePoint curvePoint);
    
    /**
     * Converts CurvePointDTO to CurvePoint entity.
     * @param curvePointDTO the curve point DTO
     * @return the curve point entity
     */
    CurvePoint toEntity(CurvePointDTO curvePointDTO);
}
