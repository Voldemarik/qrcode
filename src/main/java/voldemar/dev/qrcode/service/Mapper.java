package voldemar.dev.qrcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import voldemar.dev.qrcode.dto.output.GetLoginOutput;
import voldemar.dev.qrcode.dto.output.GetQrcodeOutput;
import voldemar.dev.qrcode.dto.output.GetParticipantOutput;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;

@Component
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);

    public GetParticipantOutput mapParticipantToDto(Participant participant) {
        log.info("Mapping participant to dto: participantId={}", participant.getId());
        return new GetParticipantOutput(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getPatronymic(),
                participant.getQrcodeList().stream()
                        .map(Qrcode::getUuid)
                        .toList()
        );
    }

    public GetQrcodeOutput mapQrcodeToDto(Qrcode qrcode) {
        return new GetQrcodeOutput(
                qrcode.getId(),
                qrcode.getUuid(),
                qrcode.getParticipantId()
        );
    }

    public Qrcode mapQrcodeDtoToEntity(GetQrcodeOutput qrcodeDto) {
        Qrcode qrcode = new Qrcode();
        qrcode.setId(qrcodeDto.id());
        qrcode.setUuid(qrcodeDto.uuid());
        qrcode.setParticipantId(qrcodeDto.participantId());

        return qrcode;
    }

    public GetLoginOutput mapParticipantToLoginDto(Participant participant) {
        return new GetLoginOutput(
                participant.getFirstName(),
                participant.getLastName(),
                participant.getPatronymic()
        );
    }
}
