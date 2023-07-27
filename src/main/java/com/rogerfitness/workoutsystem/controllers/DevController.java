package com.rogerfitness.workoutsystem.controllers;

import com.rogerfitness.workoutsystem.apis.GoogleInlineImagesResponse;
import com.rogerfitness.workoutsystem.clients.googleimages.GoogleImageClientWrapper;
import com.rogerfitness.workoutsystem.messaging.publishers.SirenAlertPublisher;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api
@Slf4j
@RestController
@RequestMapping("api/v1/dev")
public class DevController {
    private final SirenAlertPublisher sirenAlertPublisher;
    private final GoogleImageClientWrapper googleImageClientWrapper;

    public DevController(SirenAlertPublisher sirenAlertPublisher, GoogleImageClientWrapper googleImageClientWrapper) {
        this.sirenAlertPublisher = sirenAlertPublisher;
        this.googleImageClientWrapper = googleImageClientWrapper;
    }

    @GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable("message") final String message) {

        // Sending the message
        sirenAlertPublisher.sendMessage(message);

        return "Published Successfully";
    }
    @GetMapping("/google/{image}")
    public ResponseEntity<GoogleInlineImagesResponse> fetchExternalApi(@PathVariable("image") final String image) {
        return ResponseEntity.ok(googleImageClientWrapper.fetchInlineImages(image));
    }
}
