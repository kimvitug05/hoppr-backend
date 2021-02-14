CREATE TABLE destinations
(
    destination_id BIGSERIAL CONSTRAINT destinations_pk PRIMARY KEY,
    name VARCHAR NOT NULL,
    slug VARCHAR NOT NULL,
    code VARCHAR NOT NULL,
    latitude DECIMAL(15,13) NOT NULL,
    longitude DECIMAL(16,13) NOT NULL,
    google_place_id VARCHAR NOT NULL,
    image_url VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE UNIQUE index destinations_name_uindex
	ON destinations (name);

CREATE UNIQUE index destinations_slug_uindex
	ON destinations (slug);

CREATE UNIQUE index destinations_code_uindex
	ON destinations (code);

CREATE UNIQUE index destinations_google_place_id_uindex
	ON destinations (google_place_id);
