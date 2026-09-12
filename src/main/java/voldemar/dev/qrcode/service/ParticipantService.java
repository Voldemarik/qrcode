package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import voldemar.dev.qrcode.dto.input.CreateParticipantInput;
import voldemar.dev.qrcode.dto.input.UpdateParticipantInput;
import voldemar.dev.qrcode.dto.output.GetLoginOutput;
import voldemar.dev.qrcode.dto.output.GetParticipantOutput;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.repository.ParticipantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository repository;
    private final ShareAdapter adapter;
    private final Mapper mapper;

    public List<GetParticipantOutput> getAllParticipants() {
        return repository.findAllWithQrcodeList().stream()
                .map(mapper::mapParticipantToDto)
                .toList();
    }

    @Transactional
    public GetParticipantOutput createParticipant(CreateParticipantInput participantDto) {
        Participant participant = new Participant();
        participant.setFirstName(participantDto.firstName());
        participant.setLastName(participantDto.lastName());
        participant.setPatronymic(participantDto.patronymic());

        repository.save(participant);
        participant.setQrcodeList(adapter.sendCreateRequest(participant.getId()));

        return mapper.mapParticipantToDto(participant);
    }

    public GetParticipantOutput updateParticipant(Long id, UpdateParticipantInput participantDto) {
        Optional<Participant> optionalParticipant = repository.findById(id);
        if (optionalParticipant.isEmpty()) {
            throw new IllegalStateException("Participant with id = " + id + " is not exist");
        }
        Participant participant = optionalParticipant.get();

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
        Optional<Participant> optionalParticipant = repository.findById(id);
        if (optionalParticipant.isEmpty()) {
            throw new IllegalStateException("Participant with id = " + id + " is not exist");
        }
        adapter.sendDeleteRequest(id);
        repository.deleteById(id);
    }

    @Transactional
    public GetLoginOutput login(UUID uuid) {
        Long id = adapter.sendSearchRequest(uuid);
        Optional<Participant> optionalParticipant = repository.findById(id);
        if (optionalParticipant.isEmpty()) {
            throw new IllegalStateException("Participant with id = " + id + " is not exist");
        }
        Participant participant = optionalParticipant.get();

        return mapper.mapParticipantToLoginDto(participant);
    }
}
