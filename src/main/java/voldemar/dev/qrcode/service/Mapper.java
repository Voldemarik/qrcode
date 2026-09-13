package voldemar.dev.qrcode.service;

import org.springframework.stereotype.Component;
import voldemar.dev.qrcode.dto.output.LoginResponse;
import voldemar.dev.qrcode.dto.output.QrcodeResponse;
import voldemar.dev.qrcode.dto.output.ParticipantResponse;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;

@Component
public class Mapper {

    public ParticipantResponse mapParticipantToDto(Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getPatronymic(),
                participant.getQrcodeList().stream()
                        .map(Qrcode::getUuid)
                        .toList()
        );
    }

    public QrcodeResponse mapQrcodeToDto(Qrcode qrcode) {
        return new QrcodeResponse(
                qrcode.getId(),
                qrcode.getUuid(),
                qrcode.getParticipantId()
        );
    }

    public Qrcode mapQrcodeDtoToEntity(QrcodeResponse qrcodeDto) {
        Qrcode qrcode = new Qrcode();
        qrcode.setId(qrcodeDto.id());
        qrcode.setUuid(qrcodeDto.uuid());
        qrcode.setParticipantId(qrcodeDto.participantId());

        return qrcode;
    }

    public LoginResponse mapParticipantToLoginDto(Participant participant) {
        return new LoginResponse(
                participant.getFirstName(),
                participant.getLastName(),
                participant.getPatronymic()
        );
    }
}
