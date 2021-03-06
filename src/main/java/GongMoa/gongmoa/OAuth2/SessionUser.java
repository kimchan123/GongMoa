package GongMoa.gongmoa.OAuth2;

import GongMoa.gongmoa.domain.Contest.Contest;
import GongMoa.gongmoa.domain.Like;
import GongMoa.gongmoa.domain.LikeType;
import GongMoa.gongmoa.domain.Notification;
import GongMoa.gongmoa.fileupload.UploadFile;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static GongMoa.gongmoa.domain.LikeType.*;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private UploadFile picture;
    private List<Long> likeContestIds;
    private List<Long> likeNotificationIds;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.likeContestIds = user.getLikes().size() == 0 ? new ArrayList<>() :
                user.getLikes().stream().filter(l -> l.getLikeType()== CONTEST)
                        .map(l -> l.getContest().getId()).collect(Collectors.toList());
        this.likeNotificationIds = user.getLikes().size() == 0 ? new ArrayList<>() :
                user.getLikes().stream().filter(l -> l.getLikeType() == NOTIFICATION)
                .map(l -> l.getNotification().getId()).collect(Collectors.toList());
    }
}