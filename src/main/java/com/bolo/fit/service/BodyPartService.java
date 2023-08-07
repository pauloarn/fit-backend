package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.BodyPart;
import com.bolo.fit.repository.BodyPartRepository;
import com.bolo.fit.service.dto.response.BodyPartResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BodyPartService extends AbstractServiceRepo<BodyPartRepository, BodyPart, Long> {
    public BodyPartService(BodyPartRepository repository) {
        super(repository);
    }

    public List<BodyPartResponseDTO> getAllBodyParts() {
        List<BodyPart> bodyParts = repository.findAll();
        log.info("Searching all body parts");
        return bodyParts.stream().map((bp) -> new BodyPartResponseDTO().fromBodyPart(bp)).collect(Collectors.toList());
    }
    public BodyPart getExerciseTypeById(Long exerciseTypeId) throws ApiErrorException {
        return repository.findById(exerciseTypeId).orElseThrow(()-> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.BODY_PART_NOT_FOUND));
    }
}
