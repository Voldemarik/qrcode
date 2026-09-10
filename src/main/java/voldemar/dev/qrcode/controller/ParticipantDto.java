package voldemar.dev.qrcode.controller;

import java.util.List;

public record ParticipantDto(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        QrcodeDto currentQrcode,
        List<QrcodeDto> oldQrs
) {
}
