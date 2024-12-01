package com.lucky.autobg1.scheduled;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class Crown {
	
	private static final Logger log = LoggerFactory.getLogger(Crown.class);
	 private final ObjectMapper objectMapper = new ObjectMapper();

	 //@Scheduled(cron = "0 0 * * * ?")
	    private void getLogFilePath() {
		 log.info("crown is running", "inside the get method");
		 String date = LocalDate.now().toString();
	        String fileName = "log-" + date + ".json";
	        String filePath = "D:\\logs\\" + fileName; // Directory for logs

	        // Example data to log
	        Map<String, Object> logData = new HashMap<>();
	        logData.put("date", date);
	        logData.put("status", "Daily log file created successfully");

	        // Ensure the logs directory exists
	        File directory = new File("D:\\logs");
	        if (!directory.exists()) {
	            directory.mkdirs();
	            
	        }

	        // Write JSON to the file
	        try {
	            objectMapper.writeValue(Paths.get(filePath).toFile(), logData);
	            System.out.println("Log file created: " + filePath);
	        } catch (IOException e) {
	            System.err.println("Failed to create log file: " + e.getMessage());
	        }

}
}