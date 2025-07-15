-- 사용자 테이블 생성
CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    social_type VARCHAR(255),
    social_id VARCHAR(255),
    user_grade VARCHAR(10) NOT NULL DEFAULT 'LITE',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP,
    CONSTRAINT email_unique UNIQUE (email)
);