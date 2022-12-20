CREATE TABLE classe (
    num integer PRIMARY KEY,
    libelle character varying(30) NOT NULL
);



CREATE TABLE enr (
    marque integer PRIMARY KEY,
    num integer NOT NULL,
    pays character(2) NOT NULL,
    deposant integer NOT NULL,
    date_enr date NOT NULL
);




CREATE TABLE marque (
    id integer PRIMARY KEY,
    nom character varying(30) NOT NULL,
    classe integer NOT NULL,
    pays character(2) NOT NULL,
    prop integer NOT NULL
);




CREATE TABLE pays (
    code character(2) PRIMARY KEY,
    nom character varying(50) NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 16563)
-- Name: societe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE societe (
    id integer NOT NULL,
    nom character varying(40) NOT NULL,
    ville character varying(20),
    pays character(2) NOT NULL
);


--
-- TOC entry 201 (class 1259 OID 16566)
-- Name: vente; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE vente (
    marque integer NOT NULL,
    vendeur integer NOT NULL,
    acquereur integer NOT NULL,
    date_vente date NOT NULL
);
