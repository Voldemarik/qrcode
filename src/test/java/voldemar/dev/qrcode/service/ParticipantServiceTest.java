package voldemar.dev.qrcode.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import voldemar.dev.qrcode.dto.exception.NotFoundException;
import voldemar.dev.qrcode.dto.input.CreateParticipantRequest;
import voldemar.dev.qrcode.dto.input.UpdateParticipantRequest;
import voldemar.dev.qrcode.dto.output.LoginResponse;
import voldemar.dev.qrcode.dto.output.ParticipantResponse;
import voldemar.dev.qrcode.entity.Participant;
import voldemar.dev.qrcode.entity.Qrcode;
import voldemar.dev.qrcode.repository.ParticipantRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    QrcodeService mockQrcodeService;
    @Mock
    ParticipantRepository mockRepository;
    @Mock
    Mapper mockMapper;

    @InjectMocks
    ParticipantService participantService;

    private void mockParticipantSaveWithId(Long id) {
        doAnswer(invocation -> {
            Participant p = invocation.getArgument(0);
            p.setId(id);
            return p;
        }).when(mockRepository).save(any(Participant.class));
    }

    @Test
    void createParticipant_successful() {
        Long id = 123L;
        CreateParticipantRequest request = new CreateParticipantRequest("Holly", "Wild", "Doe");
        Qrcode mockQrcode = new Qrcode();
        mockQrcode.setUuid(UUID.randomUUID());
        ParticipantResponse expectedResponse = new ParticipantResponse(
                id,
                "Holly",
                "Wild",
                "Doe",
                List.of(mockQrcode.getUuid())
        );

        mockParticipantSaveWithId(id);

        when(mockQrcodeService.createQrcodeEntity(id)).thenReturn(mockQrcode);
        when(mockMapper.mapParticipantToDto(any(Participant.class))).thenReturn(expectedResponse);

        ParticipantResponse actualResponse = participantService.createParticipant(request);

        assertEquals(expectedResponse, actualResponse);

        verify(mockRepository).save(any(Participant.class));
        verify(mockQrcodeService).createQrcodeEntity(id);
        verify(mockMapper).mapParticipantToDto(any(Participant.class));
    }

    @Test
    void updateParticipant_throwNotFoundException() {
        Long id = 123L;
        UpdateParticipantRequest request = new UpdateParticipantRequest(
                null,
                "Carrol",
                null
        );

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> participantService.updateParticipant(id, request)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockRepository).findById(id);
        verify(mockRepository, never()).save(any(Participant.class));
        verify(mockMapper, never()).mapParticipantToDto(any(Participant.class));
    }

    @Test
    void updateParticipant_successful() {
        Long id = 123L;
        UpdateParticipantRequest request = new UpdateParticipantRequest(
                null,
                "Carrol",
                null
        );

        Qrcode mockQrcode = new Qrcode();
        mockQrcode.setUuid(UUID.randomUUID());

        Participant mockParticipant = new Participant();
        mockParticipant.setId(id);
        mockParticipant.setFirstName("Holly");
        mockParticipant.setLastName("Wild");
        mockParticipant.setPatronymic("Doe");

        ParticipantResponse expectedResponse = new ParticipantResponse(
                id,
                "Holly",
                "Carrol",
                "Doe",
                List.of(mockQrcode.getUuid())
        );

        when(mockRepository.findById(id)).thenReturn(Optional.of(mockParticipant));
        mockParticipantSaveWithId(id);
        when(mockMapper.mapParticipantToDto(mockParticipant)).thenReturn(expectedResponse);

        ParticipantResponse actualResponse = participantService.updateParticipant(id, request);

        assertEquals(expectedResponse, actualResponse);

        verify(mockRepository).findById(id);
        verify(mockRepository).save(any(Participant.class));
        verify(mockMapper).mapParticipantToDto(mockParticipant);
    }


    @Test
    void deleteParticipant_throwNotFoundException() {
        Long id = 123L;

        when(mockRepository.existsById(id)).thenReturn(false);

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> participantService.deleteParticipant(id)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockRepository).existsById(id);
        verify(mockRepository, never()).deleteById(id);
    }

    @Test
    void deleteParticipant_successful() {
        Long id = 123L;

        when(mockRepository.existsById(id)).thenReturn(true);

        participantService.deleteParticipant(id);

        verify(mockRepository).existsById(id);
        verify(mockRepository).deleteById(id);
    }

    @Test
    void login_throwNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Long id = 123L;

        when(mockQrcodeService.findAndRenewQrcode(uuid)).thenReturn(id);
        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> participantService.login(uuid)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockQrcodeService).findAndRenewQrcode(uuid);
        verify(mockRepository).findById(id);
        verify(mockMapper, never()).mapParticipantToLoginDto(any(Participant.class));
    }

    @Test
    void login_successful() {
        UUID uuid = UUID.randomUUID();
        Long id = 123L;

        Participant mockParticipant = new Participant();
        mockParticipant.setId(id);
        mockParticipant.setFirstName("Holly");
        mockParticipant.setLastName("Wild");
        mockParticipant.setPatronymic("Doe");

        LoginResponse expectedResponse = new LoginResponse(
                "Holly",
                "Wild",
                "Doe"
        );

        when(mockQrcodeService.findAndRenewQrcode(uuid)).thenReturn(id);
        when(mockRepository.findById(id)).thenReturn(Optional.of(mockParticipant));
        when(mockMapper.mapParticipantToLoginDto(mockParticipant)).thenReturn(expectedResponse);

        LoginResponse actualResponse = participantService.login(uuid);

        assertEquals(expectedResponse, actualResponse);

        verify(mockQrcodeService).findAndRenewQrcode(uuid);
        verify(mockRepository).findById(id);
        verify(mockMapper).mapParticipantToLoginDto(any(Participant.class));
    }
}
