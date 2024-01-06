package com.vigyanshaala.service.pdfGeneratorService;

import com.vigyanshaala.model.pdfGeneratorModel.CriticalThinking;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;

public interface CriticalThinkingServices {
    Response saveCriticalThinkingTemplate(CriticalThinking criticalThinking);
    ResponseEntity getCriticalThinkingTemplate(String studentEmailID, Long version);
    ResponseEntity getCriticalThinkingLatestVersion(String studentEmailID);
}
