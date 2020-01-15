DROP TABLE IF EXISTS banners;
DROP TABLE IF EXISTS banners_changes;
DROP TABLE IF EXISTS app_users;

CREATE TABLE IF NOT EXISTS banners
(
    banner_id serial NOT NULL,
    img_src TEXT NOT NULL,
    width integer NOT NULL,
    height integer NOT NULL,
    target_url TEXT NOT NULL,
    lang_id varchar(50) NOT NULL,

    CONSTRAINT banners_pk PRIMARY KEY (banner_id)
) WITH
      (
      OIDS=FALSE
    );

CREATE TABLE IF NOT EXISTS banners_changes
(
    banner_change_id serial NOT NULL,
    banner_id integer NOT NULL,
    admin_name varchar(50) NOT NULL,
    type_change varchar(50) NOT NULL,
    description_change TEXT,
    date_change TIMESTAMP NOT NULL,
    CONSTRAINT banners_changes_pk PRIMARY KEY (banner_change_id)
) WITH (
      OIDS=FALSE
    );

CREATE TABLE IF NOT EXISTS app_users
(
    app_user_id serial NOT NULL,
    app_user_name varchar(50) NOT NULL UNIQUE,
    app_user_encryted_password TEXT,
    CONSTRAINT app_users_pk PRIMARY KEY (app_user_id)
) WITH (
      OIDS=FALSE
    );