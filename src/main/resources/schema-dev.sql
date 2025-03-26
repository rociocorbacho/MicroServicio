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

-- Datos iniciales para desarrollo
INSERT INTO author (id, name) VALUES ('a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6', 'Gabriel Garcia Marquez');
INSERT INTO author (id, name) VALUES ('b2c3d4e5-f6g7-h8i9-j0k1-l2m3n4o5p6q7', 'Isabel Allende');

INSERT INTO book (id, title, author_id) VALUES ('c3d4e5f6-g7h8-i9j0-k1l2-m3n4o5p6q7r8', 'Cien años de soledad', 'a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6');
INSERT INTO book (id, title, author_id) VALUES ('d4e5f6g7-h8i9-j0k1-l2m3-n4o5p6q7r8s9', 'El amor en los tiempos del cólera', 'a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6');
INSERT INTO book (id, title, author_id) VALUES ('e5f6g7h8-i9j0-k1l2-m3n4-o5p6q7r8s9t0', 'La casa de los espíritus', 'b2c3d4e5-f6g7-h8i9-j0k1-l2m3n4o5p6q7');