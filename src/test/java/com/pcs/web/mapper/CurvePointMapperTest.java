package com.pcs.web.mapper;

import com.pcs.model.CurvePoint;
import com.pcs.service.CurvePointService;
import com.pcs.web.dto.CurvePointDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurvePointMapperTest {

    @InjectMocks
    private CurvePointMapper curvePointMapper;
    @Mock
    private CurvePointService curvePointService;


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
        //when
        List<CurvePointDTO> actualCurvePointDTOs = curvePointMapper.getCurvePointDTOs();
        //then
        assertEquals(expectedCurvePointDTOs, actualCurvePointDTOs);
    }

}
