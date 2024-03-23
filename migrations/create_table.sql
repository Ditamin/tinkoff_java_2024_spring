CREATE TABLE IF NOT EXISTS links (
    id bigint PRIMARY KEY,
    url varchar(2048) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT (timezone('utc', now())),
    answer_count bigint DEFAULT (100),
    comment_count bigint DEFAULT (100)
);

CREATE TABLE IF NOT EXISTS chats (
    id bigint PRIMARY KEY,
    status bigint DEFAULT(0)
);

CREATE TABLE IF NOT EXISTS connections (
    chat bigint NOT NULL,
    link bigint NOT NULL
);
