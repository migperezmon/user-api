-- Crear tabla usuario
CREATE TABLE usuario (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    last_login TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Crear tabla telefono_usuario
CREATE TABLE telefono_usuario (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    number VARCHAR NOT NULL,
    city_code INT NOT NULL,
    country_code INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES usuario(id) ON DELETE CASCADE
);