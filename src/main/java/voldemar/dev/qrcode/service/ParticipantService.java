package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import voldemar.dev.qrcode.dto.input.CreateParticipantRequest;
import voldemar.dev.qrcode.dto.input.UpdateParticipantRequest;
import voldemar.dev.qrcode.dto.output.LoginResponse;
import voldemar.dev.qrcode.dto.output.ParticipantResponse;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.dto.exception.NotFoundException;
import voldemar.dev.qrcode.repository.ParticipantRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final QrcodeService qrcodeService;
    private final ParticipantRepository repository;
    private final Mapper mapper;

    @Transactional
    public ParticipantResponse createParticipant(CreateParticipantRequest participantDto) {
        Participant participant = new Participant();
        participant.setFirstName(participantDto.firstName());
        participant.setLastName(participantDto.lastName());
        participant.setPatronymic(participantDto.patronymic());

        repository.save(participant);

        List<Qrcode> qrcodeList = new ArrayList<>();
        qrcodeList.add(qrcodeService.createQrcodeEntity(participant.getId()));
        participant.setQrcodeList(qrcodeList);

        return mapper.mapParticipantToDto(participant);
    }

    public ParticipantResponse updateParticipant(Long id, UpdateParticipantRequest participantDto) {
        Participant participant = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Participant with id = {0} is not exists", id)
                )
        );

        String firstName = participantDto.firstName();
        String lastName = participantDto.lastName();
        String patronymic = participantDto.patronymic();

        if (firstName != null && !firstName.equals(participant.getFirstName())) {
            participant.setFirstName(firstName);
        }
        if (lastName != null && !lastName.equals(participant.getLastName())) {
            participant.setLastName(lastName);
        }
        if (patronymic != null && !patronymic.equals(participant.getPatronymic())) {
            participant.setPatronymic(patronymic);
        }

        repository.save(participant);
        return mapper.mapParticipantToDto(participant);
    }

    @Transactional
    public void deleteParticipant(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(
                    MessageFormat.format("Participant with id = {0} is not exists", id)
            );
        }

        repository.deleteById(id);
    }

    @Transactional
    public LoginResponse login(UUID uuid) {
        Long id = qrcodeService.findAndRenewQrcode(uuid);
        Participant participant = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Participant with id = {0} is not exists", id)
                )
        );

        return mapper.mapParticipantToLoginDto(participant);
    }
}
