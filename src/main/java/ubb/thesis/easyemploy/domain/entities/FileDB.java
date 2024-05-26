package ubb.thesis.easyemploy.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="file")
@Getter
@Setter
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    private Long userId;
    private Long postId;
    private boolean isCV;

    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data, Long user, Long postId, boolean isCV) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.userId = user;
        this.postId = postId;
        this.isCV = isCV;
    }
}
