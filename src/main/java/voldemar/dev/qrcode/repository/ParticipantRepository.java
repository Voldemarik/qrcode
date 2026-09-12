package voldemar.dev.qrcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import voldemar.dev.qrcode.entity.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("select p from Participant p join fetch p.qrcodeList")
    List<Participant> findAllWithQrcodeList();
}
