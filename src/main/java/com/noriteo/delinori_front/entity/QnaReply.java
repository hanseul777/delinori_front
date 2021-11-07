package com.noriteo.delinori_front.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="reply_qna")
@ToString(exclude = "qna")
public class QnaReply {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long rno;

        private String replyText;

        private String replyer;

        @ManyToOne(fetch = FetchType.LAZY)
        private Qna qna;

        @CreationTimestamp
        private LocalDateTime replyDate;

        public void setText(String text){
                this.replyText = text;
        }
    }


