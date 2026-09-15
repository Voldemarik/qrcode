package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import voldemar.dev.qrcode.dto.input.UpdateQrcodeRequest;
import voldemar.dev.qrcode.dto.output.QrcodeResponse;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.dto.exception.AlreadyExistsException;
import voldemar.dev.qrcode.dto.exception.NotFoundException;
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
        return mapper.mapQrcodeToDto(createQrcodeEntity(participantId));
    }

    public Qrcode createQrcodeEntity(Long participantId) {
        Qrcode qrcode = new Qrcode();
        qrcode.setUuid(UUID.randomUUID());

        if (!participantRepository.existsById(participantId)) {
            throw new NotFoundException(
                    MessageFormat.format("Participant with id = {0} is not exists", participantId)
            );
        }
        qrcode.setParticipantId(participantId);

        repository.save(qrcode);
        return qrcode;
    }

    public QrcodeResponse updateQrcode(Long id, UpdateQrcodeRequest qrcodeDto) {
        Qrcode qrcode = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Qrcode with id = {0} is not exists", id)
                )
        );
        UUID uuid = qrcodeDto.uuid();

        if (uuid != null && !uuid.equals(qrcode.getUuid())) {
            if (repository.existsByUuid(uuid)) {
                throw new AlreadyExistsException(
                        MessageFormat.format("Qrcode with uuid = {0} is already exists", uuid)
                );
            }
            qrcode.setUuid(uuid);
        }

        Long participantId;
        if (qrcodeDto.participantId() != null) {
            participantId = qrcodeDto.participantId();
            if (!participantRepository.existsById(participantId)) {
                throw new NotFoundException(
                        MessageFormat.format("Participant with id = {0} is not exists", participantId)
                );
            }
        } else {
            participantId = null;
        }

        if (participantId != null && !participantId.equals(qrcode.getParticipantId())) {
            qrcode.setParticipantId(participantId);
        }

        repository.save(qrcode);
        return mapper.mapQrcodeToDto(qrcode);
    }

    public void deleteQrcode(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(
                    MessageFormat.format("Qrcode with id = {0} is not exists", id)
            );
        }

        repository.deleteById(id);
    }

    public Long findAndRenewQrcode(UUID uuid) {
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
