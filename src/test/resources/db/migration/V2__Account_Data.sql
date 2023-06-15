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

INSERT INTO public.account (userid, description) VALUES ('ACC000001', 'This account use for test DB');
INSERT INTO public.account (userid, description) VALUES ('ACC000002', 'This account use for test DB');
INSERT INTO public.account (userid, description) VALUES ('ACC000003', 'This account use for test DB');
INSERT INTO public.account (userid, description) VALUES ('ACC000004', 'This account use for test DB');
--
-- TOC entry 3223 (class 2606 OID 21632)
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


-- Completed on 2023-06-09 12:49:25

--
-- PostgreSQL database dump complete
--

