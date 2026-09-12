package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import voldemar.dev.qrcode.dto.input.CreateQrcodeInput;
import voldemar.dev.qrcode.dto.input.UpdateQrcodeInput;
import voldemar.dev.qrcode.dto.output.GetQrcodeOutput;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.repository.QrcodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrcodeService {

    private final QrcodeRepository repository;
    private final Mapper mapper;

    public List<GetQrcodeOutput> getAllQrcodes() {
        return repository.findAll().stream()
                .map(mapper::mapQrcodeToDto)
                .toList();
    }

    public GetQrcodeOutput createQrcode(CreateQrcodeInput qrcodeDto) {
        Qrcode qrcode = new Qrcode();
        qrcode.setUuid(UUID.randomUUID());
        qrcode.setParticipantId(qrcodeDto.participant_id());

        repository.save(qrcode);
        return mapper.mapQrcodeToDto(qrcode);
    }

    public GetQrcodeOutput updateQrcode(Long id, UpdateQrcodeInput qrcodeDto) {
        Optional<Qrcode> optionalQrcode = repository.findById(id);
        if (optionalQrcode.isEmpty()) {
            throw new IllegalStateException("Qrcode with id = " + id + " is not exist");
        }
        Qrcode qrcode = optionalQrcode.get();

        UUID uuid = qrcodeDto.uuid();
        Long participantId = qrcodeDto.participantId();

        if (uuid != null && !uuid.equals(qrcode.getUuid())) {
            Optional<Qrcode> findByUuid = repository.findByUuid(uuid);
            if (findByUuid.isPresent()) {
                throw new IllegalStateException("Qrcode with uuid is already exist");
            }
            qrcode.setUuid(uuid);
        }
        if (participantId != null && !participantId.equals(qrcode.getParticipantId())) {
            qrcode.setParticipantId(participantId);
        }

        return mapper.mapQrcodeToDto(qrcode);
    }

    public void deleteQrcode(Long id) {
        Optional<Qrcode> optionalQrcode = repository.findById(id);
        if (optionalQrcode.isEmpty()) {
            throw new IllegalStateException("Qrcode with id = " + id + " is not exist");
        }
        repository.deleteById(id);
    }

    public void deleteQrcodeList(Long id) {
        repository.deleteAllByParticipantId(id);
    }

    public Long searchQrcode(UUID uuid) {
        Optional<Qrcode> optionalQrcode = repository.findByUuid(uuid);
        if (optionalQrcode.isEmpty()) {
            throw new IllegalStateException("Qrcode with uuid = " + uuid + " is not exist");
        }
        Qrcode qrcode = optionalQrcode.get();

        return qrcode.getParticipantId();
    }
}
