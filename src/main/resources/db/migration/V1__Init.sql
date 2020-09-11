CREATE TABLE IF NOT EXISTS USER (
    id          INT AUTO_INCREMENT,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    firstname   VARCHAR(255),
    lastname    VARCHAR(255),
    email       VARCHAR(255),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS URL_DETAIL (
   id				INT AUTO_INCREMENT,
   url				VARCHAR(1200),
   short_url		VARCHAR(100)NOT NULL,
   creation_time	DATETIME NOT NULL,
   expiration_time	DATETIME,

   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS GROUP_CONTEXT (
   id				INT AUTO_INCREMENT,
   title			VARCHAR(255) NOT NULL,
   group_type		VARCHAR(255) NOT NULL,
   creator          INT NOT NULL,
   descripition		VARCHAR(255),

   PRIMARY KEY(id),
   FOREIGN KEY(creator) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS GROUP_ADMIN (
   id				INT AUTO_INCREMENT,
   group_id			INT NOT NULL,
   user_id			INT NOT NULL,
   status			VARCHAR(32) NOT NULL,

   PRIMARY KEY(id),
   FOREIGN KEY(user_id) REFERENCES GROUP_CONTEXT(id),
   FOREIGN KEY(group_id) REFERENCES GROUP_CONTEXT(id)
);

CREATE TABLE IF NOT EXISTS CARD (
   id				INT AUTO_INCREMENT,
   url_detail_id	INT NOT NULL,
   group_id         INT NOT NULL,
   creator			INT NOT NULL,
   title			VARCHAR(32) NOT NULL,
   status			VARCHAR(32) NOT NULL,
   descripition		VARCHAR(255),
   icon				BLOB,

   PRIMARY KEY(id),
   FOREIGN KEY(url_detail_id) REFERENCES URL_DETAIL(id),
   FOREIGN KEY(group_id) REFERENCES GROUP_CONTEXT(id),
   FOREIGN KEY(creator) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS FAVORITES (
    id              INT AUTO_INCREMENT,
    user_id			INT NOT NULL,
    card_id         INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(card_id) REFERENCES CARD(id)
);
