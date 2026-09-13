package voldemar.dev.qrcode.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record ParticipantResponse(
        Long id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String patronymic,
        @JsonProperty("qrcode_list")
        List<UUID> qrcodeList
) {
}
