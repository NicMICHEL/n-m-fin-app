package com.pcs.web.mapper;

import com.pcs.model.CurvePoint;
import com.pcs.service.CurvePointService;
import com.pcs.web.dto.CurvePointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurvePointMapper {

    @Autowired
    private CurvePointService curvePointService;

    public CurvePointDTO toCurvePointDTO(CurvePoint curvePoint) {
        return new CurvePointDTO(
                curvePoint.getId(),
                Integer.toString(curvePoint.getCurveId()),
                Double.toString(curvePoint.getTerm()),
                Double.toString(curvePoint.getValue())
        );
    }

    public CurvePoint toCurvePoint(CurvePointDTO curvePointDTO) {
        //test to avoid setting other attributes to zero
        if (curvePointDTO.getId() == null) {
            return new CurvePoint(
                    Integer.parseInt(curvePointDTO.getCurveId()),
                    Double.parseDouble(curvePointDTO.getTerm()),
                    Double.parseDouble(curvePointDTO.getValue())
            );
        } else {
            CurvePoint curvePoint = curvePointService.getById(curvePointDTO.getId());
            curvePoint.setCurveId(Integer.parseInt(curvePointDTO.getCurveId()));
            curvePoint.setTerm(Double.parseDouble(curvePointDTO.getTerm()));
            curvePoint.setValue(Double.parseDouble(curvePointDTO.getValue()));
            return curvePoint;
        }
    }

}
