package voldemar.dev.qrcode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.dto.input.CreateParticipantRequest;
import voldemar.dev.qrcode.dto.input.UpdateParticipantRequest;
import voldemar.dev.qrcode.dto.output.LoginResponse;
import voldemar.dev.qrcode.dto.output.ParticipantResponse;
import voldemar.dev.qrcode.service.ParticipantService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private final ParticipantService service;

    @PutMapping("login")
    public LoginResponse login(@RequestParam UUID uuid) {
        return service.login(uuid);
    }

    @PostMapping
    public ParticipantResponse createParticipant(@RequestBody CreateParticipantRequest participantDto) {
        return service.createParticipant(participantDto);
    }

    @PutMapping("{id}")
    public ParticipantResponse updateParticipant(
            @PathVariable Long id,
            @RequestBody UpdateParticipantRequest participantDto
    ) {
        return service.updateParticipant(id, participantDto);
    }

    @DeleteMapping("{id}")
    public void deleteParticipant(@PathVariable Long id) {
        service.deleteParticipant(id);
    }
}
