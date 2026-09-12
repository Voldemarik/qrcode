package voldemar.dev.qrcode.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.dto.input.CreateParticipantInput;
import voldemar.dev.qrcode.dto.input.UpdateParticipantInput;
import voldemar.dev.qrcode.dto.output.GetLoginOutput;
import voldemar.dev.qrcode.dto.output.GetParticipantOutput;
import voldemar.dev.qrcode.service.ParticipantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private static final Logger log = LoggerFactory.getLogger(ParticipantController.class);
    private final ParticipantService service;

    @PutMapping("login")
    public GetLoginOutput login(@RequestParam UUID uuid) {
        return service.login(uuid);
    }

    @GetMapping
    public List<GetParticipantOutput> getAllParticipants() {
        log.info("Called method getAllParticipants()");
        return service.getAllParticipants();
    }

    @PostMapping
    public GetParticipantOutput createParticipant(@RequestBody CreateParticipantInput participantDto) {
        return service.createParticipant(participantDto);
    }

    @PutMapping("{id}")
    public GetParticipantOutput updateParticipant(
            @PathVariable Long id,
            @RequestBody UpdateParticipantInput participantDto
    ) {
        return service.updateParticipant(id, participantDto);
    }

    @DeleteMapping("{id}")
    public void deleteParticipant(@PathVariable Long id) {
        service.deleteParticipant(id);
    }
}
