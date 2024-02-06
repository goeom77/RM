package org.gyu.solution.measured_data.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.service.DataUsageService;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.gyu.solution.log_data.dao.LogDataDao;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.measured_data.dao.MeasuredDataDao;
import org.gyu.solution.measured_data.dto.MeasuredDataDto;
import org.gyu.solution.measured_data.entity.MeasuredData;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageIn;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageOut;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasuredDataServiceImpl implements MeasuredDataService{
    private final MeasuredDataDao measuredDataDao;
    private final DataUsageService dataUsageService;
    private final LogDataDao logDataDao; // 임시 로그 데이터 조회용 -> 추후 measurement_dao로 변경

    @Override
    public void insertMeasuredDataByMonthDate(LocalDateTime measuredDate,LogData logData) {
        measuredDataDao.insertMeasuredDataByMonthDate(measuredDate, logData);
    }
    /*
     * 추후 정산 로직을 하루에 한번으로 변경하고 비용을 청구값과 같이 조회로 변경
     * data_usage테이블에서 service_id를 통해 사용자 수를 가져온다.
     * log_data에서는 storage_usage를 가져온다. 가장 최근 것을 가져온다.
     * rm_service에서는 service_type, storage_size, limit_user, created_date, expiration_date를 가져온다.
     */
    @Override
    public MeasuredDataUsageOut getMeasuredData(MeasuredDataUsageIn measuredDataUsageIn, LocalDateTime now) {
        int usedUser = dataUsageService.countByServiceIdAndStatus(measuredDataUsageIn.getServiceId(), true);
        MeasuredDataDto dto = logDataDao.findMeasuredDataDtoByServiceId(measuredDataUsageIn.getServiceId());
        if(dto == null) throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
        return MeasuredDataUsageOut.builder()
                .serviceId(measuredDataUsageIn.getServiceId())
                .serviceType(dto.getServiceType())
                .storageUsage(dto.getStorageUsage())
                .storageData(dto.getStorageSize())
                .limitUser(dto.getLimitUser())
                .usedUser(usedUser)
                .expirationDate(dto.getExpirationDate())
                .now(LocalDateTime.now())
                .build();

    }
}
