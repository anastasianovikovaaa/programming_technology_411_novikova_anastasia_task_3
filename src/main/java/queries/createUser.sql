--CREATE TABLE [IF NOT EXISTS] [test].UserTable (
CREATE TABLE UserTable (
    id_user INTEGER PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    address TEXT NOT NULL,
    phone TEXT NOT NULL UNIQUE
);
