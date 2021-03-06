package GongMoa.gongmoa.repository;

import GongMoa.gongmoa.domain.Notification;
import GongMoa.gongmoa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByNotification(Notification notification);
}
