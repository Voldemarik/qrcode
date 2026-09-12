package voldemar.dev.qrcode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import voldemar.dev.qrcode.dto.input.CreateQrcodeInput;
import voldemar.dev.qrcode.dto.input.UpdateQrcodeInput;
import voldemar.dev.qrcode.dto.output.GetQrcodeOutput;
import voldemar.dev.qrcode.service.QrcodeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/qrcodes")
public class QrcodeController {

    private final QrcodeService service;

    @GetMapping
    public List<GetQrcodeOutput> getAllQrcodes() {
        return service.getAllQrcodes();
    }

    @PostMapping
    public GetQrcodeOutput createQrcode(@RequestBody CreateQrcodeInput qrcodeDto) {
        return service.createQrcode(qrcodeDto);
    }

    @PutMapping("{id}")
    public GetQrcodeOutput updateQrcode(
            @PathVariable Long id,
            @RequestBody UpdateQrcodeInput qrcodeDto
    ) {
        return service.updateQrcode(id, qrcodeDto);
    }

    @DeleteMapping("{id}")
    public void deleteQrcode(@PathVariable Long id) {
        service.deleteQrcode(id);
    }
}
