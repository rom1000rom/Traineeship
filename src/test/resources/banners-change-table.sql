
CREATE TABLE banners_changes
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

