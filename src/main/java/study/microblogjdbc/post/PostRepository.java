package study.microblogjdbc.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final NamedParameterJdbcTemplate namedJdbc;

    public Page<Post> findPosts(Pageable pageable) {
        String totalCountSql = "SELECT COUNT(*) FROM post";
        Integer totalCount = namedJdbc.queryForObject(totalCountSql, new MapSqlParameterSource(), Integer.class);

        String sql = """
            SELECT p.*
            FROM post p
            ORDER BY id DESC
            LIMIT :limit OFFSET :offset
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<Post> posts = namedJdbc.query(sql, params, new BeanPropertyRowMapper<>(Post.class));

        return new PageImpl<>(posts, pageable, totalCount == null ? 0 : totalCount);
    }

    public Post findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = :id";
        
        return namedJdbc.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Post.class)
        );
    }

    public void save(Post post) {
        String sql = """
            INSERT INTO post (title, content, created_at)
            VALUES (:title, :content, :createdAt)
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent())
                .addValue("createdAt", post.getCreatedAt());

        namedJdbc.update(sql, params);
    }

    public void update(Post post) {
        String sql = """
            UPDATE post
            SET title = :title,
                content = :content
            WHERE id = :id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", post.getId())
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent());

        namedJdbc.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM post WHERE id = :id";
        
        namedJdbc.update(
                sql,
                new MapSqlParameterSource("id", id)
        );
    }
}