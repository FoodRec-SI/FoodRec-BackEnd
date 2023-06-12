--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-09 12:06:24

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
-- TOC entry 229 (class 1259 OID 21704)
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--


--
-- TOC entry 228 (class 1259 OID 21703)
-- Name: post_status_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--


--
-- TOC entry 3373 (class 0 OID 21704)
-- Dependencies: 229
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.post (postid, status, userid, moderatorid, recipename, description, calories, duration, image, "time", recipeid) VALUES ('POS000001', 2, 'ACC000001', 'ACC000004', 'bánh xèo', '1 loại bánh ngon vcl', 50, 120, '\x00', '2023-06-06 13:54:25.306751', 'REC000001');
INSERT INTO public.post (postid, status, userid, moderatorid, recipename, description, calories, duration, image, "time", recipeid) VALUES ('POS000002', 1, 'ACC000001', null, 'bánh xèo C', '1 loại bánh ngon vcl', 50, 120, '\x00', '2023-06-06 13:54:25.306751', 'REC000004');


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 228
-- Name: post_status_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.post_status_seq', 1, false);


--
-- TOC entry 3224 (class 2606 OID 21710)
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 3221 (class 2606 OID 21993)
-- Name: post status; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 3222 (class 1259 OID 21862)
-- Name: fki_post_username_fkey; Type: INDEX; Schema: public; Owner: postgres
--


--
-- TOC entry 3225 (class 1259 OID 21994)
-- Name: post_recipeid_fkey; Type: INDEX; Schema: public; Owner: postgres
--


--
-- TOC entry 3226 (class 2606 OID 22032)
-- Name: post fki4kh1uspt4ppy89vu0oaxk2w1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 3227 (class 2606 OID 21863)
-- Name: post post_morderatorname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 3228 (class 2606 OID 22027)
-- Name: post post_recipeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 3229 (class 2606 OID 21868)
-- Name: post post_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

-- Completed on 2023-06-09 12:06:24

--
-- PostgreSQL database dump complete
--

