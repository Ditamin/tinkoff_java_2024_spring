CREATE TABLE IF NOT EXISTS links (
    id bigint PRIMARY KEY,
    url varchar(2048) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT (timezone('utc', now()))
);

CREATE TABLE IF NOT EXISTS chats (
    id bigint PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS connections (
    chat bigint NOT NULL,
    link bigint NOT NULL
);
