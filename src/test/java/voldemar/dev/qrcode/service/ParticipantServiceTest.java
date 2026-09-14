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
    ParticipantService mockService;

    @Test
    void testCreateParticipantSuccessful() {
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

        doAnswer(invocation -> {
            Participant p = invocation.getArgument(0);
            p.setId(id);
            return p;
        }).when(mockRepository).save(any(Participant.class));

        when(mockQrcodeService.createQrcodeEntity(id)).thenReturn(mockQrcode);
        when(mockMapper.mapParticipantToDto(any(Participant.class))).thenReturn(expectedResponse);

        ParticipantResponse actualResponse = mockService.createParticipant(request);

        assertEquals(expectedResponse, actualResponse);

        verify(mockRepository, times(1)).save(any(Participant.class));
        verify(mockQrcodeService, times(1)).createQrcodeEntity(id);
        verify(mockMapper, times(1)).mapParticipantToDto(any(Participant.class));
    }

    @Test
    void testUpdateParticipantThrowNotFoundException() {
        Long id = 123L;
        UpdateParticipantRequest request = new UpdateParticipantRequest(
                null,
                "Carrol",
                null
        );

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> mockService.updateParticipant(id, request)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, never()).save(any(Participant.class));
        verify(mockMapper, never()).mapParticipantToDto(any(Participant.class));
    }

    @Test
    void testUpdateParticipantSuccessful() {
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
        doAnswer(invocation -> {
            Participant p = invocation.getArgument(0);
            p.setId(id);
            return p;
        }).when(mockRepository).save(any(Participant.class));
        when(mockMapper.mapParticipantToDto(mockParticipant)).thenReturn(expectedResponse);

        ParticipantResponse actualResponse = mockService.updateParticipant(id, request);

        assertEquals(expectedResponse, actualResponse);

        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(1)).save(any(Participant.class));
        verify(mockMapper, times(1)).mapParticipantToDto(mockParticipant);
    }

    @Test
    void testDeleteParticipantThrowNotFoundException() {
        Long id = 123L;

        when(mockRepository.existsById(id)).thenReturn(false);

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> mockService.deleteParticipant(id)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockRepository, times(1)).existsById(id);
        verify(mockRepository, never()).deleteById(id);
    }

    @Test
    void testDeleteParticipantSuccessful() {
        Long id = 123L;

        when(mockRepository.existsById(id)).thenReturn(true);

        mockService.deleteParticipant(id);

        verify(mockRepository, times(1)).existsById(id);
        verify(mockRepository, times(1)).deleteById(id);
    }

    @Test
    void testLoginThrowNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Long id = 123L;

        when(mockQrcodeService.findAndRenewQrcode(uuid)).thenReturn(id);
        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> mockService.login(uuid)
        );

        assertEquals(MessageFormat.format("Participant with id = {0} is not exists", id), ex.getMessage());

        verify(mockQrcodeService, times(1)).findAndRenewQrcode(uuid);
        verify(mockRepository, times(1)).findById(id);
        verify(mockMapper, never()).mapParticipantToLoginDto(any(Participant.class));
    }

    @Test
    void testLoginSuccessful() {
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

        LoginResponse actualResponse = mockService.login(uuid);

        assertEquals(expectedResponse, actualResponse);

        verify(mockQrcodeService, times(1)).findAndRenewQrcode(uuid);
        verify(mockRepository, times(1)).findById(id);
        verify(mockMapper, times(1)).mapParticipantToLoginDto(any(Participant.class));
    }
}