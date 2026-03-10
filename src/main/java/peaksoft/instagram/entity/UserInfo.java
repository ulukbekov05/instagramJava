package peaksoft.instagram.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.instagram.enums.Gender;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String biography;
    private Gender gender;

    @Column(name = "string_image")
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}