package voldemar.dev.qrcode.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record QrcodeResponse(
    Long id,
    UUID uuid,
    @JsonProperty("participant_id")
    Long participantId
) {
}
