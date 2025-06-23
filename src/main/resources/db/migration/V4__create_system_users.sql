CREATE TABLE system_users (
     id UUID PRIMARY KEY,
     active BOOLEAN NOT NULL
);

CREATE TABLE system_user_roles (
      user_id UUID NOT NULL REFERENCES system_users(id) ON DELETE CASCADE,
      role VARCHAR(255) NOT NULL
);

CREATE INDEX idx_system_user_roles_user_id ON system_user_roles(user_id);
