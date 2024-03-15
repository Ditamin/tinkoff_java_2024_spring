CREATE TABLE IF NOT EXISTS links (
    id bigint PRIMARY KEY,
    url varchar(2048) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS chats (
    id bigint PRIMARY KEY,
    links bigint[]
);


