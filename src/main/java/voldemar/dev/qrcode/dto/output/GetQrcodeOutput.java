package voldemar.dev.qrcode.dto.output;

import java.util.UUID;

public record GetQrcodeOutput(
    Long id,
    UUID uuid,
    Long participantId
) {
}
