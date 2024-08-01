package com.pcs.web.mapper;

import com.pcs.model.CurvePoint;
import com.pcs.web.dto.CurvePointDTO;
import org.springframework.stereotype.Component;

@Component
public class CurvePointMapper {

    public CurvePointDTO toCurvePointDTO(CurvePoint curvePoint) {
        return new CurvePointDTO(
                curvePoint.getId(),
                Integer.toString(curvePoint.getCurveId()),
                Double.toString(curvePoint.getTerm()),
                Double.toString(curvePoint.getValue())
        );
    }

    public CurvePoint toCurvePoint(CurvePointDTO curvePointDTO) {
        return new CurvePoint(
                curvePointDTO.getId(),
                Integer.parseInt(curvePointDTO.getCurveId()),
                Double.parseDouble(curvePointDTO.getTerm()),
                Double.parseDouble(curvePointDTO.getValue())
        );
    }

}
