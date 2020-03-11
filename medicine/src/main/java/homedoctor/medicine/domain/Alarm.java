package homedoctor.medicine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Alarm extends DateTimeEntity {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_user", nullable = false)
    private User user;

    @Column(name = "alarm_title", length = 64, nullable = false)
    private String title;

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_label", nullable = false)
    private Label label;

    @Column(name = "alarm_start_date", length = 10, nullable = false)
    private Date startDate;

    @Column(name = "alarm_end_date", length = 10, nullable = false)
    private Date endDate;

    @Column(name = "alarm_times", length = 128, nullable = false)
    private String times;

    @Column(name = "alarm_repeats", length = 32, nullable = false)
    private String repeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_enabled", nullable = false)
    private AlarmStatus alarmStatus;

    @OneToMany(mappedBy = "alarm", cascade = ALL)
    private List<PrescriptionImage> prescriptionImageList = new ArrayList<>();

    //== 연관관계 메서드 ==//
//    public void creatAlarm(User user) {
//        this.user = user;
//        user.getAlarms().add(this);
//    }

    @Builder
    public Alarm(User user, String title, Label label, Date startDate, Date endDate, String times, String repeats, AlarmStatus alarmStatus, List<PrescriptionImage> prescriptionImageList) {
        this.user = user;
        this.title = title;
        this.label = label;
        this.startDate = startDate;
        this.endDate = endDate;
        this.times = times;
        this.repeats = repeats;
        this.alarmStatus = alarmStatus;
        this.prescriptionImageList = prescriptionImageList;
    }
}