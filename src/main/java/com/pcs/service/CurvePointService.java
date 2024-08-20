package com.pcs.service;

import com.pcs.model.CurvePoint;
import com.pcs.repository.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    private static final Logger logger = LogManager.getLogger(CurvePointService.class);

    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    public CurvePoint getById(Integer id) throws IllegalArgumentException {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            return curvePoint.get();
        } else {
            logger.error("Unable to find curvePoint corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid curvePoint id");
        }
    }

    @Transactional
    public void save(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Transactional
    public void update(CurvePoint curvePoint) throws IllegalArgumentException {
        Optional<CurvePoint> testCurvePoint = curvePointRepository.findById(curvePoint.getId());
        if (testCurvePoint.isPresent()) {
            curvePointRepository.save(curvePoint);
        } else {
            logger.error("Unable to find and update curvePoint corresponding to id {}", curvePoint.getId());
            throw new IllegalArgumentException("Invalid curvePoint id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            curvePointRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete curvePoint corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid curvePoint id");
        }
    }

}
