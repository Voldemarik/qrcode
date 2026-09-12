package voldemar.dev.qrcode.dto.input;

public record UpdateParticipantInput(
    String firstName,
    String lastName,
    String patronymic
) {
}
