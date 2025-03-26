CREATE TABLE IF NOT EXISTS author (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author(id)
);

-- Datos mínimos para pruebas
INSERT INTO author (id, name) VALUES ('test-author-1', 'Autor de Prueba');
INSERT INTO book (id, title, author_id) VALUES ('test-book-1', 'Libro de Prueba', 'test-author-1');