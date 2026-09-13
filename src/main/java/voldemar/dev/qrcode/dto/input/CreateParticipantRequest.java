package voldemar.dev.qrcode.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateParticipantRequest(
        @NotNull
        @Size(min = 2)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 2)
        @JsonProperty("last_name")
        String lastName,

        @NotNull
        @Size(min = 2)
        String patronymic
) {
}
