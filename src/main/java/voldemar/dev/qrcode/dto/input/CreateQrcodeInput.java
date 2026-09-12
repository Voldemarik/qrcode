package voldemar.dev.qrcode.dto.input;

import jakarta.validation.constraints.NotNull;

public record CreateQrcodeInput(
        @NotNull
        Long participant_id
) {
}
