package voldemar.dev.qrcode.dto.input;

import java.util.UUID;

public record UpdateQrcodeInput(
        UUID uuid,
        Long participantId
) {
}
