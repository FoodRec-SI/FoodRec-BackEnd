--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-09 12:13:05

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
-- TOC entry 218 (class 1259 OID 21648)
-- Name: recipe; Type: TABLE; Schema: public; Owner: postgres
--


--
-- TOC entry 3367 (class 0 OID 21648)
-- Dependencies: 218
-- Data for Name: recipe; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000001', 'bánh xèo', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000002', 'bánh xèo A', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000003', 'bánh xèo B', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000004', 'bánh xèo C', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000005', 'bánh xèo D', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000006', 'bánh xèo E', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000007', 'bánh xèo F', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000008', 'bánh xèo G', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000009', 'bánh xèo H', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);
INSERT INTO public.recipe (recipeid, recipename, description, calories, username, duration, image, status) VALUES ('REC000010', 'bánh xèo I', '1 loại bánh ngon vcl', 50, 'namsieuquay', 120, '\x00', true);


--
-- TOC entry 3223 (class 2606 OID 22001)
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 3224 (class 2606 OID 21912)
-- Name: recipe recipe_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--


-- Completed on 2023-06-09 12:13:05

--
-- PostgreSQL database dump complete
--

