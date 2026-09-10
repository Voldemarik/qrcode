package voldemar.dev.qrcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.service.ParticipantService;

import java.util.List;

@RestController
@RequestMapping(path = "api/")
public class ParticipantController {
    private static final Logger log = LoggerFactory.getLogger(ParticipantController.class);
    private final ParticipantService service;

    public ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping("/participants")
    public List<ParticipantDto> getAllParticipants() {
        log.info("Called method getAllParticipants()");
        return service.getAllParticipants();
    }

    @PostMapping("/participants")
    public ParticipantDto createParticipant(@RequestBody ParticipantDto participantDto) {
        return service.createParticipant(participantDto);
    }
}
