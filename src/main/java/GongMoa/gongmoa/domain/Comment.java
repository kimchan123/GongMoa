package GongMoa.gongmoa.domain;

import GongMoa.gongmoa.OAuth2.User;
import GongMoa.gongmoa.domain.Contest.Contest;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(mappedBy = "superComment", cascade = CascadeType.ALL)
    private List<Comment> subComments = new ArrayList<>();

    private Boolean isDeleted;

    public Comment() {

    }

    public Comment(User user, Contest contest, String content, Boolean isDeleted) {
        this.content = content;
        this.contest = contest;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public static Comment createComment(User user, Contest contest, String content) {
        Comment comment = new Comment(user, contest, content, false);

        user.getComments().add(comment);
        contest.getComments().add(comment);

        return comment;
    }

    public void setSuperComment(Comment superComment) {
        this.superComment = superComment;
    }
}
