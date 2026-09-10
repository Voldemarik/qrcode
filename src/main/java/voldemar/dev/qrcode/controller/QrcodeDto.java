package voldemar.dev.qrcode.controller;

import java.util.UUID;

public record QrcodeDto(
    UUID id,
    Boolean status,
    Long participantId
) {
}
