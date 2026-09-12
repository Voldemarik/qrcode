package voldemar.dev.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import voldemar.dev.qrcode.dto.input.CreateQrcodeInput;
import voldemar.dev.qrcode.entity.Qrcode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShareAdapter {

    private final QrcodeService qrcodeService;
    private final Mapper mapper;

    public List<Qrcode> sendCreateRequest(Long id) {
        CreateQrcodeInput qrcodeDto = new CreateQrcodeInput(id);
        List<Qrcode> qrcodeList = new ArrayList<>();
        qrcodeList.add(mapper.mapQrcodeDtoToEntity(qrcodeService.createQrcode(qrcodeDto)));

        return qrcodeList;
    }

    public void sendDeleteRequest(Long id) {
        qrcodeService.deleteQrcodeList(id);
    }

    public Long sendSearchRequest(UUID uuid) {
        return qrcodeService.searchQrcode(uuid);
    }
}
