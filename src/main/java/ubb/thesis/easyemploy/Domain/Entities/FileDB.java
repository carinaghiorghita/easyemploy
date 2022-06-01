package ubb.thesis.easyemploy.Domain.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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

    private String username;
    private boolean isCV;

    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data, String user, boolean isCV) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.username = user;
        this.isCV = isCV;
    }
}
