package com.noriteo.delinori_front.repository.search;

import com.noriteo.delinori_front.entity.QQna;
import com.noriteo.delinori_front.entity.QQnaPicture;
import com.noriteo.delinori_front.entity.QQnaReply;
import com.noriteo.delinori_front.entity.Qna;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class QnaSearchImpl extends QuerydslRepositorySupport implements QnaSearch{

    //동적쿼리 entity지정
    public QnaSearchImpl() {
        super(Qna.class);
    }

    @Override
    public Page<Qna> search1(char[] typeArr, String keyword, Pageable pageable) {

        log.info("=============Search1==============");

        QQna qna = QQna.qna;
        JPQLQuery<Qna> jpqlQuery = from(qna);

        if(typeArr != null && typeArr.length > 0){
            BooleanBuilder condition = new BooleanBuilder();

            for(char type: typeArr){
                if (type == 'T'){
                    condition.or(qna.title.contains(keyword));
                }else if(type == 'C'){
                    condition.or(qna.content.contains(keyword));
                }else if (type == 'W'){
                    condition.or(qna.writer.contains(keyword));
                }
            }
            jpqlQuery.where(condition);
        }
        //qno > 0
        jpqlQuery.where(qna.qno.gt(0L));

        //페이징
        JPQLQuery<Qna> pagingQuery =
                this.getQuerydsl().applyPagination(pageable, jpqlQuery);

        List<Qna> qnaList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

        return new PageImpl<>(qnaList, pageable, totalCount);
    }

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable) {

        log.info("searchWithReplyCount");

        QQna qQna = QQna.qna;
        QQnaReply qQnaReply = QQnaReply.qnaReply;

        JPQLQuery<Qna> query = from(qQna);
        query.leftJoin(qQnaReply).on(qQnaReply.qna.eq(qQna));

        query.groupBy(qQna);

        if(typeArr != null && typeArr.length > 0){

            BooleanBuilder condition = new BooleanBuilder();

            for(char type: typeArr){
                if(type == 'T'){
                    condition.or(qQna.title.contains(keyword));
                }else if(type =='C'){
                    condition.or(qQna.content.contains(keyword));
                }else if(type == 'W'){
                    condition.or(qQna.writer.contains(keyword));
                }
            }
            query.where(condition);
        }

        JPQLQuery<Tuple> selectQuery =
                query.select(qQna.qno, qQna.title, qQna.writer, qQna.regDate, qQnaReply.count());

        this.getQuerydsl().applyPagination(pageable, selectQuery);

        log.info(selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();

        long totalCount = selectQuery.fetchCount();

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        return new PageImpl<>(arr, pageable, totalCount);
    }

    @Override
    public Page<Object[]> getSearchList(Pageable pageable) {

        log.info("getSearchList........");
        QQna qQna = QQna.qna;

        QQnaPicture qQnaPicture = new QQnaPicture("pic");

        JPQLQuery<Qna> query = from(qQna);

        query.leftJoin(qQna.pictures, qQnaPicture);

        query.where(qQnaPicture.idx.eq(0));
        query.groupBy(qQna);

        JPQLQuery<Tuple> tupleJPQLQuery = query.select(qQna.qno, qQna.title, qQnaPicture);

        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);

        log.info("query : " + query);

        List<Tuple> tupleList = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();

        return new PageImpl<>(tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList()),pageable, count);
    }
}
