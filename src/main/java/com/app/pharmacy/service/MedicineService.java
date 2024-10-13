package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.common.CommonDeleteResponse;
import com.app.pharmacy.domain.common.CommonGetResponse;
import com.app.pharmacy.domain.dto.medicine.CreateMedicineRequest;
import com.app.pharmacy.domain.dto.medicine.GetMedicineRequest;
import com.app.pharmacy.domain.dto.medicine.MedicineResponse;
import com.app.pharmacy.domain.dto.medicine.UpdateMedicineRequest;
import com.app.pharmacy.domain.entity.Medicine;
import com.app.pharmacy.exception.CustomResponseException;
import com.app.pharmacy.exception.ErrorCode;
import com.app.pharmacy.mapper.MedicineMapper;
import com.app.pharmacy.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.pharmacy.specification.MedicineSpecifications.hasCategory;
import static com.app.pharmacy.specification.MedicineSpecifications.hasName;
import static com.app.pharmacy.specification.MedicineSpecifications.hasQuantity;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final Clock clock;

    public ApiResponse<CommonGetResponse<MedicineResponse>> getMedicines(GetMedicineRequest request, Pageable pageable) {
        ApiResponse<CommonGetResponse<MedicineResponse>> response = new ApiResponse<>();

        Specification<Medicine> specification = Specification.where(
                hasName(request.name())
                        .and(hasCategory(request.category())
                                .and(hasQuantity(request.quantity()))));
        Page<Medicine> medicinesPage = medicineRepository.findAll(specification, pageable);
        List<MedicineResponse> medicineResponses = MedicineMapper.INSTANCE.toListMedicineResponse(medicinesPage.getContent());
        response.setData(new CommonGetResponse<>(
                medicineResponses,
                medicinesPage.getSize(),
                medicinesPage.getNumber(),
                medicinesPage.getTotalElements()));
        response.setMessage("Fetched medicines");
        return response;
    }

    public ApiResponse<MedicineResponse> createMedicine(
            CreateMedicineRequest request, Authentication connectedUser) {

        ApiResponse<MedicineResponse> response = new ApiResponse<>();
        Medicine medicine = MedicineMapper.INSTANCE.toEntity(request);
        medicine.setCreatedBy(connectedUser.getName());
        medicine.setCreatedDate(LocalDateTime.now(clock));
        medicineRepository.save(medicine);

        MedicineResponse medicineResponse = MedicineMapper.INSTANCE.toMedicineResponse(medicine);
        response.setMessage("Created medicine!");
        response.setData(medicineResponse);
        return response;
    }

    public ApiResponse<MedicineResponse> updateMedicine(
            String medicineId, UpdateMedicineRequest request, Authentication connectedUser) {
        ApiResponse<MedicineResponse> response = new ApiResponse<>();
        medicineRepository.findById(medicineId).ifPresentOrElse(medicine -> {
            MedicineMapper.INSTANCE.toEntity(request, medicine);
            medicine.setUpdatedBy(connectedUser.getName());
            medicine.setUpdatedDate(LocalDateTime.now(clock));
            medicineRepository.save(medicine);

            MedicineResponse medicineResponse = MedicineMapper.INSTANCE.toMedicineResponse(medicine);
            response.setData(medicineResponse);
            response.setMessage("Updated medicine!");
        }, () -> {
            throw new CustomResponseException(ErrorCode.MEDICINE_NOT_EXIST);
        });

        return response;
    }

    public ApiResponse<CommonDeleteResponse> deleteMedicine(String medicineId) {
        ApiResponse<CommonDeleteResponse> response = new ApiResponse<>();
        medicineRepository.findById(medicineId).ifPresentOrElse(medicineRepository::delete, () -> {
            throw new CustomResponseException(ErrorCode.MEDICINE_NOT_EXIST);
        });
        response.setData(new CommonDeleteResponse(medicineId));
        response.setMessage("Deleted medicine!");
        return response;
    }
}
