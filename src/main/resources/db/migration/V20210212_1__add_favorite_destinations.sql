CREATE TABLE IF NOT EXISTS favorite_destinations
(
    user_id VARCHAR NOT NULL,
    destination_id BIGINT NOT NULL REFERENCES destinations (destination_id),
    created_date TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT favorite_destinations_pk PRIMARY KEY (user_id, destination_id)
);
