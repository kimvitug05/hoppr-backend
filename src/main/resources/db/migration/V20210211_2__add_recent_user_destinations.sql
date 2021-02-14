CREATE TABLE IF NOT EXISTS recent_user_destinations
(
    user_id VARCHAR NOT NULL,
    destination_id BIGINT NOT NULL REFERENCES destinations (destination_id),
    last_visited_date TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT recent_user_destinations_pk PRIMARY KEY (user_id, destination_id)
);
