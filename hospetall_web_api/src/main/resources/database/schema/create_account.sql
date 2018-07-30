
CREATE TABLE account(
  id INT IDENTITY NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY(id)
)

CREATE TABLE authority(
  id INT IDENTITY NOT NULL,
  authority VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id)
)

CREATE TABLE account_authority(
  account_id INT,
  authority_id INT,
  FOREIGN KEY (account_id) REFERENCES account(id),
  FOREIGN KEY (authority_id) REFERENCES authority(id)
  PRIMARY KEY (account_id, authority_id)
)
