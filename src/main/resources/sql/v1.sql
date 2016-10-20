CREATE TABLE files(
  id SERIAL PRIMARY KEY,
  name CHAR(50) NOT NULL,
  file BYTEA,
  session BIGINT
)

CREATE TABLE results (
  id  SERIAL PRIMARY KEY,
  session BIGINT,
  result BYTEA
)