package voldemar.dev.qrcode.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String patronymic;

    @OneToOne
    @JoinColumn(name = "current_qrcode_id")
    private Qrcode currentQrcode;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private List<Qrcode> oldQrs = new ArrayList<>();
}
