package voldemar.dev.qrcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import voldemar.dev.qrcode.entity.Qrcode;

import java.util.UUID;

@Repository
public interface QrcodeRepository extends JpaRepository<Qrcode, UUID> {
}
