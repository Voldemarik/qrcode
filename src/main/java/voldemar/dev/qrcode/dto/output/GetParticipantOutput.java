package voldemar.dev.qrcode.dto.output;

import java.util.List;
import java.util.UUID;

public record GetParticipantOutput(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        List<UUID> qrcodeList
) {
}
