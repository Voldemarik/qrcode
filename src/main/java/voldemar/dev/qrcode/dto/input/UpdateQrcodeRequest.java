package voldemar.dev.qrcode.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UpdateQrcodeRequest(
        UUID uuid,
        @JsonProperty("participant_id")
        Long participantId
) {
}
