package voldemar.dev.qrcode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.dto.input.CreateParticipantInput;
import voldemar.dev.qrcode.dto.input.UpdateParticipantInput;
import voldemar.dev.qrcode.dto.output.GetLoginOutput;
import voldemar.dev.qrcode.dto.output.GetParticipantOutput;
import voldemar.dev.qrcode.service.ParticipantService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private final ParticipantService service;

    @PutMapping("login")
    public GetLoginOutput login(@RequestParam UUID uuid) {
        return service.login(uuid);
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
