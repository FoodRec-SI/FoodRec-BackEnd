--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-09 12:49:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';


--
-- TOC entry 214 (class 1259 OID 21627)
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

--
-- TOC entry 3366 (class 0 OID 21627)
-- Dependencies: 214
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('74007e14-840e-44f0-bc8c-99e3e9d1674c', 'Test account for DB', NULL, NULL, NULL);
INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('d5fecf38-589e-42c2-bc2c-ec49cc300137', 'Test account for DB', NULL, NULL, NULL);
INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('90ba5460-1798-4a8f-bdc0-62d065ca1c08', 'Test account for DB', NULL, NULL, NULL);
INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('59b8fdc0-42df-4a28-bcb1-e0651dbb08a1', 'Test account for DB', NULL, NULL, NULL);
INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('1f13ef69-46ad-42c7-aa00-04cd546c2164', 'Test account for DB', NULL, NULL, NULL);
INSERT INTO public.account (userid, description, "profile-image", "background-image", name) VALUES ('ec1f01cf-1255-4987-bdd0-3ba039747648', 'Test account for DB', NULL, NULL, NULL);
--
-- TOC entry 3223 (class 2606 OID 21632)
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


-- Completed on 2023-06-09 12:49:25

--
-- PostgreSQL database dump complete
--

