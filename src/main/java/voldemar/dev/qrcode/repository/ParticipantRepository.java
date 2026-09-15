package voldemar.dev.qrcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import voldemar.dev.qrcode.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
