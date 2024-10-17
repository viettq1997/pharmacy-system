package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.common.CommonDeleteResponse;
import com.app.pharmacy.domain.common.CommonGetResponse;
import com.app.pharmacy.domain.dto.locationrack.CreateLocationRackRequest;
import com.app.pharmacy.domain.dto.locationrack.GetLocationRackRequest;
import com.app.pharmacy.domain.dto.locationrack.LocationRackResponse;
import com.app.pharmacy.domain.dto.locationrack.UpdateLocationRackRequest;
import com.app.pharmacy.domain.entity.LocationRack;
import com.app.pharmacy.exception.CustomResponseException;
import com.app.pharmacy.exception.ErrorCode;
import com.app.pharmacy.mapper.LocationRackMapper;
import com.app.pharmacy.repository.LocationRackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.pharmacy.specification.LocationRackSpecifications.hasPosition;

@Service
@RequiredArgsConstructor
public class LocationRackService {

    private final LocationRackRepository locationrackRepository;
    private final Clock clock;

    public ApiResponse<LocationRackResponse> createLocationRack(CreateLocationRackRequest request, Authentication connectedUser) {
        ApiResponse<LocationRackResponse> response = new ApiResponse<>();
        LocationRack locationrack = LocationRackMapper.INSTANCE.toEntity(request);
        locationrack.setCreatedBy(connectedUser.getName());
        locationrack.setCreatedDate(LocalDateTime.now(clock));
        locationrackRepository.save(locationrack);

        LocationRackResponse locationrackResponse = LocationRackMapper.INSTANCE.toLocationRackResponse(locationrack);
        response.setData(locationrackResponse);
        return response;
    }

    public ApiResponse<CommonGetResponse<LocationRackResponse>> getLocationRacks(GetLocationRackRequest request, Pageable pageable) {
        ApiResponse<CommonGetResponse<LocationRackResponse>> response = new ApiResponse<>();

        Specification<LocationRack> specification = Specification.where(
                hasPosition(request.position()));
        Page<LocationRack> locationRacksPage = locationrackRepository.findAll(specification, pageable);
        List<LocationRackResponse> locationRackResponses = LocationRackMapper.INSTANCE.toListLocationRackResponse(locationRacksPage.getContent());
        response.setData(new CommonGetResponse<>(
                locationRackResponses,
                locationRacksPage.getSize(),
                locationRacksPage.getNumber(),
                locationRacksPage.getTotalElements()));
        return response;
    }

    public ApiResponse<LocationRackResponse> updateLocationRack(
            String locationRackId, UpdateLocationRackRequest request, Authentication connectedUser) {
        ApiResponse<LocationRackResponse> response = new ApiResponse<>();
        locationrackRepository.findById(locationRackId).ifPresentOrElse(locationrack -> {
            LocationRackMapper.INSTANCE.toEntity(request, locationrack);
            locationrack.setUpdatedBy(connectedUser.getName());
            locationrack.setUpdatedDate(LocalDateTime.now(clock));
            locationrackRepository.save(locationrack);

            LocationRackResponse locationrackResponse = LocationRackMapper.INSTANCE.toLocationRackResponse(locationrack);
            response.setData(locationrackResponse);
        }, () -> {
            throw new CustomResponseException(ErrorCode.LOCATION_RACK_NOT_EXIST);
        });

        return response;
    }

    public ApiResponse<CommonDeleteResponse> deleteLocationRack(String locationRackId) {
        ApiResponse<CommonDeleteResponse> response = new ApiResponse<>();
        locationrackRepository.findById(locationRackId).ifPresentOrElse(locationrackRepository::delete, () -> {
            throw new CustomResponseException(ErrorCode.LOCATION_RACK_NOT_EXIST);
        });
        response.setData(new CommonDeleteResponse(locationRackId));
        return response;
    }
}
