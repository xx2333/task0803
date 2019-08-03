package service;

import java.util.List;
import java.util.Map;

public interface ReportService {


    List<Integer> getMemberCountsByMonths(List<String> months);

    List<Map<String, Object>> getSetmealReport();

    Map<String, Object> getBusinessReportData() throws Exception;
}
