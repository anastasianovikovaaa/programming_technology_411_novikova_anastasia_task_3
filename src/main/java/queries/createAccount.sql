--CREATE TABLE [IF NOT EXISTS] [test].AccountTable (
CREATE TABLE AccountTable (
    id BLOB PRIMARY KEY DEFAULT (randomblob(16)),
    client_id      INTEGER NOT NULL,
    amount DECIMAL(7,2) DEFAULT (0.00),
    accCode TEXT NOT NULL,
        FOREIGN KEY (client_id)
        REFERENCES UserTable (id_user)
);