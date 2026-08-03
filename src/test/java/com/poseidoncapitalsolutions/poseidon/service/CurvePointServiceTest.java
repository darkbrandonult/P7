package com.poseidoncapitalsolutions.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.CurvePointMapper;
import com.poseidoncapitalsolutions.poseidon.model.CurvePoint;
import com.poseidoncapitalsolutions.poseidon.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvePointMapper curvePointMapper;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;
    private CurvePointDTO curvePointDTO;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(5.0);
        curvePoint.setValue(99.5);

        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setCurveId(10);
        curvePointDTO.setTerm(5.0);
        curvePointDTO.setValue(99.5);
    }

    @Test
    void findAllShouldReturnListOfCurvePointDTOs() {
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));
        when(curvePointMapper.toDto(curvePoint)).thenReturn(curvePointDTO);

        List<CurvePointDTO> result = curvePointService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getCurveId());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnCurvePointDTOWhenFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointMapper.toDto(curvePoint)).thenReturn(curvePointDTO);

        Optional<CurvePointDTO> result = curvePointService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(10, result.get().getCurveId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(curvePointRepository.findById(999)).thenReturn(Optional.empty());

        Optional<CurvePointDTO> result = curvePointService.findById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistAndReturnCurvePointDTO() {
        when(curvePointMapper.toEntity(curvePointDTO)).thenReturn(curvePoint);
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        when(curvePointMapper.toDto(curvePoint)).thenReturn(curvePointDTO);

        CurvePointDTO result = curvePointService.save(curvePointDTO);

        assertNotNull(result);
        assertEquals(10, result.getCurveId());
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(curvePointRepository).deleteById(anyInt());

        curvePointService.deleteById(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }
}