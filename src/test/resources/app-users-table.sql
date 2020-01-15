CREATE TABLE IF NOT EXISTS  app_users
(
    app_user_id serial NOT NULL,
    app_user_name varchar(50) NOT NULL UNIQUE,
    app_user_encryted_password TEXT,
    CONSTRAINT app_users_pk PRIMARY KEY (app_user_id)
) WITH (
      OIDS=FALSE
    );