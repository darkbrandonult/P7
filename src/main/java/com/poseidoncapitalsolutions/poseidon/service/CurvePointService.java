package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.CurvePointMapper;
import com.poseidoncapitalsolutions.poseidon.model.CurvePoint;
import com.poseidoncapitalsolutions.poseidon.repository.CurvePointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CurvePointService implements GenericService<CurvePointDTO> {
    
    private final CurvePointRepository curvePointRepository;
    private final CurvePointMapper curvePointMapper;
    
    public CurvePointService(CurvePointRepository curvePointRepository, CurvePointMapper curvePointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvePointMapper = curvePointMapper;
    }
    
    @Override
    public List<CurvePointDTO> findAll() {
        return curvePointRepository.findAll().stream()
                .map(curvePointMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<CurvePointDTO> findById(Integer id) {
        return curvePointRepository.findById(id)
                .map(curvePointMapper::toDto);
    }
    
    @Override
    public CurvePointDTO save(CurvePointDTO dto) {
        CurvePoint curvePoint = curvePointMapper.toEntity(dto);
        CurvePoint saved = curvePointRepository.save(curvePoint);
        return curvePointMapper.toDto(saved);
    }
    
    @Override
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
