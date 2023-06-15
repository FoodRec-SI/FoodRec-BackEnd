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

INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000019', 'cơm xèo', '1 loại cơm ngon', 47, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000021', 'bún riêu cua', '1 loại bún truyền thống', 76, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000023', 'cafe sữa đá', '1 loại cafe đặc trưng của Việt Nam', 67, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000016', 'bánh xèo', '1 loại bánh', 87, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000017', 'bánh bao', '1 loại bánh bao', 79, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000018', 'cơm chiên', '1 loại cơm chiên', 76, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000020', 'mì quảng', '1 loại mì siêu đỉnh', 47, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000022', 'sinh tố xoài', '1 loại sinh tố thơm ngon', 76, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000024', 'trà sữa', '1 loại trà cực ngon', 34, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000025', 'bánh xèo', '1 loại bánh siêu mềm', 7, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000026', 'bánh kem ABC', '1 loại bánh kem cực cuốn', 8, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);
INSERT INTO public.recipe (recipeid, recipename, description, calories, userid, duration, image, status, username) VALUES ('REC000027', 'cơm niêu quảng ninh', 'cơm niêu không thể thiếu của mọi nhà', 22, '74007e14-840e-44f0-bc8c-99e3e9d1674c', 200, '\x00', true, NULL);



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

