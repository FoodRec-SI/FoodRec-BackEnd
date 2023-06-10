-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.account
(
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    password character varying(32) COLLATE pg_catalog."default" NOT NULL,
    role character varying(10) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    status boolean NOT NULL DEFAULT true,
    CONSTRAINT account_pkey PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS public.plan
(
    planid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    planname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    mealid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT plan_pkey PRIMARY KEY (planid)
);

CREATE TABLE IF NOT EXISTS public.meal
(
    mealid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    mealname character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT meal_pkey PRIMARY KEY (mealid)
);

CREATE TABLE IF NOT EXISTS public.meal_recipe
(
    recipeid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    mealid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT meal_recipe_pkey PRIMARY KEY (mealid, recipeid)
);

CREATE TABLE IF NOT EXISTS public.recipe
(
    recipeid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    recipename character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    calories integer NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    duration integer NOT NULL,
    image bytea NOT NULL,
    status boolean NOT NULL DEFAULT true,
    CONSTRAINT recipe_pkey PRIMARY KEY (recipeid)
);

CREATE TABLE IF NOT EXISTS public.instruction
(
    recipeid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL
);

CREATE TABLE IF NOT EXISTS public.recipe_ingredient
(
    recipeid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    ingredientid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT recipe_ingredient_pkey PRIMARY KEY (recipeid, ingredientid)
);

CREATE TABLE IF NOT EXISTS public.ingredient
(
    ingredientid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT ingredient_pkey PRIMARY KEY (ingredientid)
);

CREATE TABLE IF NOT EXISTS public.list
(
    listid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    ingredientid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    planid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT list_pkey PRIMARY KEY (listid)
);

CREATE TABLE IF NOT EXISTS public.recipe_collection
(
    collectionid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    recipeid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT recipe_collection_pkey PRIMARY KEY (collectionid, recipeid)
);

CREATE TABLE IF NOT EXISTS public.collection
(
    collectionid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    collectionname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT collection_pkey PRIMARY KEY (collectionid)
);

CREATE TABLE IF NOT EXISTS public.post
(
    postid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    moderatorname character varying(255) COLLATE pg_catalog."default",
    recipename character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    calories integer NOT NULL,
    duration integer NOT NULL,
    image bytea NOT NULL,
    "time" timestamp(6) without time zone NOT NULL,
    recipeid character varying(255) COLLATE pg_catalog."default",
    recipe_recipeid character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT post_pkey PRIMARY KEY (postid)
);

CREATE TABLE IF NOT EXISTS public.notification
(
    postid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT notification_pkey PRIMARY KEY (postid, username)
);

CREATE TABLE IF NOT EXISTS public.rating
(
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    postid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    score smallint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 32767 CACHE 1 ),
    CONSTRAINT rating_pkey PRIMARY KEY (username, postid)
);

CREATE TABLE IF NOT EXISTS public.recipe_tag
(
    tagid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    recipeid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT recipe_tag_pkey PRIMARY KEY (tagid, recipeid)
);

CREATE TABLE IF NOT EXISTS public.tag
(
    tagid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    tagname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (tagid)
);

CREATE TABLE IF NOT EXISTS public.account_tag
(
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    tagid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_tag_pkey PRIMARY KEY (username, tagid)
);

ALTER TABLE IF EXISTS public.plan
    ADD CONSTRAINT plan_mealid_fkey FOREIGN KEY (mealid)
    REFERENCES public.meal (mealid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.plan
    ADD CONSTRAINT plan_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.meal_recipe
    ADD CONSTRAINT meal_recipe_mealid_fkey FOREIGN KEY (mealid)
    REFERENCES public.meal (mealid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.meal_recipe
    ADD CONSTRAINT meal_recipe_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe
    ADD CONSTRAINT recipe_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.instruction
    ADD CONSTRAINT instruction_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_ingredient
    ADD CONSTRAINT recipe_ingredient_ingredient_fkey FOREIGN KEY (ingredientid)
    REFERENCES public.ingredient (ingredientid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS recipe_ingredient_ingredient_fkey
    ON public.recipe_ingredient(ingredientid);


ALTER TABLE IF EXISTS public.recipe_ingredient
    ADD CONSTRAINT recipe_ingredient_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS recipe_ingredient_recipeid_fkey
    ON public.recipe_ingredient(recipeid);


ALTER TABLE IF EXISTS public.list
    ADD CONSTRAINT list_ingredientid_fkey FOREIGN KEY (ingredientid)
    REFERENCES public.ingredient (ingredientid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.list
    ADD CONSTRAINT list_planid_fkey FOREIGN KEY (planid)
    REFERENCES public.plan (planid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_collection
    ADD CONSTRAINT recipe_collection_collectionid_fkey FOREIGN KEY (collectionid)
    REFERENCES public.collection (collectionid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_collection
    ADD CONSTRAINT recipe_collection_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.collection
    ADD CONSTRAINT collection_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.post
    ADD CONSTRAINT fki4kh1uspt4ppy89vu0oaxk2w1 FOREIGN KEY (recipe_recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.post
    ADD CONSTRAINT post_morderatorname_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS fki_post_username_fkey
    ON public.post(username);


ALTER TABLE IF EXISTS public.post
    ADD CONSTRAINT post_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS post_recipeid_fkey
    ON public.post(recipeid);


ALTER TABLE IF EXISTS public.post
    ADD CONSTRAINT post_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS fki_post_username_fkey
    ON public.post(username);


ALTER TABLE IF EXISTS public.notification
    ADD CONSTRAINT notification_postid_fkey FOREIGN KEY (postid)
    REFERENCES public.post (postid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.notification
    ADD CONSTRAINT notification_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.rating
    ADD CONSTRAINT rating_postid_fkey FOREIGN KEY (postid)
    REFERENCES public.post (postid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.rating
    ADD CONSTRAINT rating_username_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_tag
    ADD CONSTRAINT fkbjmgrxyyjq1k6bikkucgunlnh FOREIGN KEY (recipeid)
    REFERENCES public.tag (tagid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_tag
    ADD CONSTRAINT fkby55d1unxkpjl6h9lnu5d7ek3 FOREIGN KEY (tagid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_tag
    ADD CONSTRAINT recipe_tag_recipeid_fkey FOREIGN KEY (recipeid)
    REFERENCES public.recipe (recipeid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.recipe_tag
    ADD CONSTRAINT recipe_tag_tagid_fkey FOREIGN KEY (tagid)
    REFERENCES public.tag (tagid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.account_tag
    ADD CONSTRAINT account_tag_accountid_fkey FOREIGN KEY (username)
    REFERENCES public.account (username) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS account_tag_accountid_fkey
    ON public.account_tag(username);


ALTER TABLE IF EXISTS public.account_tag
    ADD CONSTRAINT account_tag_tagid_fkey FOREIGN KEY (tagid)
    REFERENCES public.tag (tagid) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;