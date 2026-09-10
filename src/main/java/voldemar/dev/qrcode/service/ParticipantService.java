package voldemar.dev.qrcode.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import voldemar.dev.qrcode.controller.ParticipantDto;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.repository.ParticipantRepository;
import voldemar.dev.qrcode.repository.QrcodeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final QrcodeRepository qrcodeRepository;
    private final Mapper mapper;

    public ParticipantService(ParticipantRepository participantRepository, QrcodeRepository qrcodeRepository, Mapper mapper) {
        this.participantRepository = participantRepository;
        this.qrcodeRepository = qrcodeRepository;
        this.mapper = mapper;
    }

    public List<ParticipantDto> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(mapper::mapParticipantToDto)
                .toList();
    }

    @Transactional
    public ParticipantDto createParticipant(ParticipantDto participantDto) {
        Participant entity = new Participant();

        entity.setFirstName(participantDto.firstName());
        entity.setLastName(participantDto.lastName());
        entity.setPatronymic(participantDto.patronymic());

        Qrcode qrcode = new Qrcode();
        qrcode.setId(UUID.randomUUID());
        qrcode.setStatus(true);

        qrcodeRepository.save(qrcode);

        entity.setCurrentQrcode(qrcode);
        participantRepository.save(entity);

        qrcode.setParticipantId(entity.getId());
        qrcodeRepository.save(qrcode);

        return mapper.mapParticipantToDto(entity);
    }
}
