package com.pcs.service;

import com.pcs.model.CurvePoint;
import com.pcs.repository.CurvePointRepository;
import com.pcs.web.dto.CurvePointDTO;
import com.pcs.web.mapper.CurvePointMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;
    @Mock
    private CurvePointMapper curvePointMapper;
    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    public void should_throw_an_exception_when_getting_curvePoint_corresponding_to_id_is_not_found() {
        //given
        Optional<CurvePoint> emptyCurvePoint = Optional.empty();
        when(curvePointRepository.findById(anyInt())).thenReturn(emptyCurvePoint);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    curvePointService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid curvePoint id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_curvePoint_corresponding_to_id_is_not_found() {
        //given
        CurvePoint testCurvePoint = new CurvePoint();
        testCurvePoint.setId(55);
        Optional<CurvePoint> emptyCurvePoint = Optional.empty();
        when(curvePointRepository.findById(55)).thenReturn(emptyCurvePoint);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    curvePointService.update(testCurvePoint);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid curvePoint id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_curvePoint_corresponding_to_id_is_not_found() {
        //given
        Optional<CurvePoint> emptyCurvePoint = Optional.empty();
        when(curvePointRepository.findById(anyInt())).thenReturn(emptyCurvePoint);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    curvePointService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid curvePoint id", thrown.getMessage());
    }

    @Test
    public void should_get_curvePointDTOs_successfully() throws Exception {
        //given
        CurvePoint curvePoint1 = new CurvePoint(1, 2, 2.2, 3.3);
        CurvePoint curvePoint2 = new CurvePoint(2, 3, 3.3, 4.4);
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint1);
        curvePoints.add(curvePoint2);
        CurvePointDTO curvePointDTO1 = new CurvePointDTO(1, "2", "2.2", "3.3");
        CurvePointDTO curvePointDTO2 = new CurvePointDTO(2, "3", "3.3", "4.4");
        List<CurvePointDTO> expectedCurvePointDTOs = new ArrayList<>();
        expectedCurvePointDTOs.add(curvePointDTO1);
        expectedCurvePointDTOs.add(curvePointDTO2);
        when(curvePointService.getCurvePoints()).thenReturn(curvePoints);
        when(curvePointMapper.toCurvePointDTO(curvePoint1)).thenReturn(curvePointDTO1);
        when(curvePointMapper.toCurvePointDTO(curvePoint2)).thenReturn(curvePointDTO2);
        //when
        List<CurvePointDTO> actualCurvePointDTOs = curvePointService.getCurvePointDTOs();
        //then
        assertEquals(expectedCurvePointDTOs, actualCurvePointDTOs);
    }

}
