package voldemar.dev.qrcode.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateParticipantRequest(
    @JsonProperty("first_name")
    String firstName,
    @JsonProperty("last_name")
    String lastName,
    String patronymic
) {
}
