
CREATE TABLE banners
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


