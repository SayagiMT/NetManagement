package com.NetProject.service;

import com.NetProject.dto.RevenueDTO;

import java.util.List;

public interface ReportService {
    List<RevenueDTO> getDailyRevenue();
}
