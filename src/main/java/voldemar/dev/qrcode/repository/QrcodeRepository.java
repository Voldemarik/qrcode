package voldemar.dev.qrcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import voldemar.dev.qrcode.entity.Qrcode;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrcodeRepository extends JpaRepository<Qrcode, Long> {
    Optional<Qrcode> findByUuid(UUID uuid);

    void deleteAllByParticipantId(Long id);
}
