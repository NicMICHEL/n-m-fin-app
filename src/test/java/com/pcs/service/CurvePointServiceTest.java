package com.pcs.service;

import com.pcs.model.CurvePoint;
import com.pcs.repository.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;
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

}
