package com.jo0oy.myblog.domain.posts;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.jo0oy.myblog.domain.members.QMember.member;
import static com.jo0oy.myblog.domain.posts.QPost.post;

@Slf4j
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findOneWithMember(Long postId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(post)
                        .innerJoin(post.member, member)
                        .fetchJoin()
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }

    @Override
    public List<Post> findAllWithMember() {
        return queryFactory.selectFrom(post)
                .innerJoin(post.member, member)
                .fetchJoin()
                .fetch();
    }

    @Override
    public Page<Post> findAllWithMember(Pageable pageable) {

        List<Post> content = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable))
                .fetch();

        JPAQuery<Post> countQuery = queryFactory.selectFrom(post)
                .innerJoin(post.member, member)
                .fetchJoin();

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Post> findAllByAuthorId(Pageable pageable, Long authorId) {
        List<Post> content = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .where(post.member.id.eq(authorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable))
                .fetch();

        JPAQuery<Post> countQuery = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .where(post.member.id.eq(authorId));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Post> findAllByUsername(Pageable pageable, String username) {

        List<Post> content = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .where(post.member.username.eq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable))
                .fetch();

        JPAQuery<Post> countQuery = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .where(post.member.username.eq(username));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Post> findAllByConditions(Pageable pageable, PostSearchCondition condition) {

        List<Post> content = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .fetchJoin()
                .where(titleContains(condition.getTitle()),
                        authorContains(condition.getAuthor()),
                        startDateGoe(condition.getStartDate()),
                        endDateLt(condition.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable))
                .fetch();

        JPAQuery<Post> countQuery = queryFactory.selectFrom(post)
                .leftJoin(post.member, member)
                .where(titleContains(condition.getTitle()),
                        authorContains(condition.getAuthor()),
                        startDateGoe(condition.getStartDate()),
                        endDateLt(condition.getEndDate()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression authorContains(String author) {
        return StringUtils.hasText(author) ? post.member.username.contains(author) : null;
    }

    private BooleanExpression startDateGoe(LocalDate createdAtGoe) {
        return Objects.nonNull(createdAtGoe) ? post.createdDate.goe(createdAtGoe.atStartOfDay()) : null;
    }

    private BooleanExpression endDateLt(LocalDate createdAtLoe) {
        return Objects.nonNull(createdAtLoe) ? post.createdDate.lt(createdAtLoe.plusDays(1).atStartOfDay()) : null;
    }

    /**
     * OrderSpecifier ??? ????????? ???????????? ??????????????? ????????????.
     * ????????? ??????
     * @param  pageable
     * @return OrderSpecifier
     */
    private OrderSpecifier<?> postSort(Pageable pageable) {
        //??????????????? ????????? Pageable ????????? ???????????? null ??? ??????
        if (!pageable.getSort().isEmpty()) {
            //???????????? ?????? ????????? for ???????????? ?????? ????????????
            for (Sort.Order order : pageable.getSort()) {
                // ??????????????? ????????? DESC or ASC ??? ????????????.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // ??????????????? ????????? ?????? ????????? ????????? ????????? ?????? ???????????? ???????????? ??????.
                switch (order.getProperty()){
                    case "id":
                        return new OrderSpecifier<>(direction, post.id);
                    case "title":
                        return new OrderSpecifier<>(direction, post.title);
                    case "content":
                        return new OrderSpecifier<>(direction, post.content);
                    case "member":
                        return new OrderSpecifier<>(direction, post.member.username);
                    case "createdDate":
                        return new OrderSpecifier<>(direction, post.createdDate);
                }
            }
        }
        return null;
    }
}
