CREATE TABLE IF NOT EXISTS owner
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    first_name
    VARCHAR
(
    100
) NOT NULL,
    last_name VARCHAR
(
    100
) NOT NULL,
    keycloak_user_id VARCHAR
(
    100
)
    );

CREATE INDEX IF NOT EXISTS idx_owner_keycloak_user_id ON owner(keycloak_user_id);
