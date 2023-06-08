package com.example.api;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {


    @GetMapping("/time")
    public String getTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return "API Response: Local time is " + currentTime.toString();
    }



    @GetMapping("/time_ip")
    public TimeResponse get_JasonTimeIP(HttpServletRequest request) {
        LocalDateTime currentTime = LocalDateTime.now();
        String clientIp = getClientIp(request);
        return new TimeResponse(currentTime.toString(), clientIp);
    }
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws IOException {
        Resource resource = new ClassPathResource("static/image.jpg");
        byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}
