package voldemar.dev.qrcode.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateParticipantInput(
        @NotNull
        @Size(min = 2)
        String firstName,

        @NotNull
        @Size(min = 2)
        String lastName,

        @NotNull
        @Size(min = 2)
        String patronymic
) {
}
