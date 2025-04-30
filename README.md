# Mirco blog Jdbc to Jpa(branches 분리)

> 구성 환경: Java17, Spring boot3, Spring Data Jpa, Jdbc, MySQL, Thymeleaf

## MySQL 실행
```Shell
brew update
brew install mysql
mysql.server start
```

## 스키마 생성
```sql
-- 1) 스키마(데이터베이스)부터 만든다. 없으면 새로 생성
CREATE DATABASE IF NOT EXISTS micro_blog

-- 2) 이후 작업 대상 스키마를 명시적으로 선택
USE micro_blog;

-- 3) 같은 이름의 테이블이 이미 있다면 깨끗하게 지운다
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;

-- 4) 테이블 생성
CREATE TABLE post (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE comment (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id    BIGINT NOT NULL,
    content    TEXT   NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_post
        FOREIGN KEY (post_id)
        REFERENCES post (id)
        ON DELETE CASCADE
) ENGINE = InnoDB;

-- 5) 샘플 데이터 삽입
INSERT INTO post (title, content) VALUES
('첫 번째 게시글', '블로그 예제 시작!'),
('두 번째 게시글', 'thymeleaf와 jdbc를 이용한 예제');

INSERT INTO comment (post_id, content) VALUES
(1, '첫 댓글!'),
(1, '두 번째 댓글'),
(2, '두 번째 게시글의 첫 댓글');

```

## 실행 방법
MySQL 실행, DB 스키마 생성 이후 애플리케이션 실행

http://localhost:8080/posts 접속
