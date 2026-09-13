package voldemar.dev.qrcode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.dto.input.UpdateQrcodeRequest;
import voldemar.dev.qrcode.dto.output.QrcodeResponse;
import voldemar.dev.qrcode.service.QrcodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/qrcodes")
public class QrcodeController {

    private final QrcodeService service;

    @PostMapping
    public QrcodeResponse createQrcode(@RequestParam("participant_id") Long participantId) {
        return service.createQrcode(participantId);
    }

    @PutMapping("{id}")
    public QrcodeResponse updateQrcode(
            @PathVariable Long id,
            @RequestBody UpdateQrcodeRequest qrcodeDto
    ) {
        return service.updateQrcode(id, qrcodeDto);
    }

    @DeleteMapping("{id}")
    public void deleteQrcode(@PathVariable Long id) {
        service.deleteQrcode(id);
    }
}
