package voldemar.dev.qrcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import voldemar.dev.qrcode.controller.ParticipantDto;
import voldemar.dev.qrcode.controller.QrcodeDto;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;

@Component
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);

    public ParticipantDto mapParticipantToDto(Participant participant) {
        log.info("Mapping participant to dto: participantId={}", participant.getId());
        return new ParticipantDto(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getPatronymic(),
                mapQrcodeToDto(participant.getCurrentQrcode()),
                participant.getOldQrs().stream()
                        .filter(e -> e.getStatus() == false)
                        .map(this::mapQrcodeToDto)
                        .toList()
        );
    }

    public QrcodeDto mapQrcodeToDto(Qrcode qrcode) {
        return new QrcodeDto(
                qrcode.getId(),
                qrcode.getStatus(),
                qrcode.getParticipantId()
        );
    }
}
