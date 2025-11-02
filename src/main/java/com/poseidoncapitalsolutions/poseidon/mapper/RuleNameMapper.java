package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.poseidon.model.RuleName;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for RuleName entity and RuleNameDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RuleNameMapper {
    
    /**
     * Converts RuleName entity to RuleNameDTO.
     * @param ruleName the rule name entity
     * @return the rule name DTO
     */
    RuleNameDTO toDto(RuleName ruleName);
    
    /**
     * Converts RuleNameDTO to RuleName entity.
     * @param ruleNameDTO the rule name DTO
     * @return the rule name entity
     */
    RuleName toEntity(RuleNameDTO ruleNameDTO);
}
