CREATE TABLE IF NOT EXISTS login (
    id          BIGSERIAL    PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS tasks (
    id          BIGSERIAL    PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL REFERENCES login(username),
    title       VARCHAR(255) NOT NULL,
    content     TEXT,
    name        VARCHAR(100),
    start_date  DATE,
    end_date    DATE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_tasks_username ON tasks (username);