package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import voldemar.dev.qrcode.dto.input.UpdateQrcodeRequest;
import voldemar.dev.qrcode.dto.output.QrcodeResponse;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.exception.AlreadyExistsException;
import voldemar.dev.qrcode.exception.NotFoundException;
import voldemar.dev.qrcode.repository.ParticipantRepository;
import voldemar.dev.qrcode.repository.QrcodeRepository;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrcodeService {

    private final ParticipantRepository participantRepository;
    private final QrcodeRepository repository;
    private final Mapper mapper;

    public QrcodeResponse createQrcode(Long participantId) {
        Qrcode qrcode = new Qrcode();
        qrcode.setUuid(UUID.randomUUID());

        participantRepository.findById(participantId).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Participant with id = {0} is not exists", participantId)
                )
        );
        qrcode.setParticipantId(participantId);

        repository.save(qrcode);
        return mapper.mapQrcodeToDto(qrcode);
    }

    public QrcodeResponse updateQrcode(Long id, UpdateQrcodeRequest qrcodeDto) {
        Qrcode qrcode = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Qrcode with id = {0} is not exists", id)
                )
        );
        UUID uuid = qrcodeDto.uuid();

        if (uuid != null && !uuid.equals(qrcode.getUuid())) {
            repository.findByUuid(uuid).ifPresent(
                    qrcode1 -> { throw new AlreadyExistsException(
                            MessageFormat.format("Qrcode with uuid = {0} is already exists", uuid)
                    );
            });
            qrcode.setUuid(uuid);
        }

        Long participantId;
        if (qrcodeDto.participantId().describeConstable().isEmpty()) {
            participantId = qrcodeDto.participantId();

            participantRepository.findById(participantId).orElseThrow(
                    () -> new NotFoundException(
                            MessageFormat.format("Participant with id = {0} is not exists", participantId)
                    )
            );
        } else {
            participantId = null;
        }

        if (participantId != null && !participantId.equals(qrcode.getParticipantId())) {
            qrcode.setParticipantId(participantId);
        }

        return mapper.mapQrcodeToDto(qrcode);
    }

    public void deleteQrcode(Long id) {
        repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Qrcode with id = {0} is not exists", id)
                )
        );

        repository.deleteById(id);
    }

    public void deleteQrcodeList(Long id) {
        repository.deleteAllByParticipantId(id);
    }

    public Long searchQrcode(UUID uuid) {
        Qrcode qrcode = repository.findByUuid(uuid).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Qrcode with uuid = {0} is not exists", uuid)
                )
        );
        qrcode.setUuid(UUID.randomUUID());

        repository.save(qrcode);
        return qrcode.getParticipantId();
    }
}
