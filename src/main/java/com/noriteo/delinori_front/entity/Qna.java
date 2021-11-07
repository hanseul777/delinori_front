package com.noriteo.delinori_front.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"pictures"})
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    private String title;
    private String content;
    private String writer;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime modDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diary_qna_pricture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<QnaPicture> pictures;


    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictures(Set<QnaPicture> pictures) {
        this.pictures = pictures;
    }
}
