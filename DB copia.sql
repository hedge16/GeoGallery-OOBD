-- CREATE DATABASE Galleria IF NOT EXISTS;
-- DROP SCHEMA IF EXISTS galleria_schema CASCADE;

-- CREAZIONE SCHEMA
CREATE SCHEMA IF NOT EXISTS galleria_schema;

-- CREAZIONE TABELLA UTENTE
CREATE TABLE IF NOT EXISTS galleria_schema.utente (
	nome VARCHAR(100),
	cognome VARCHAR(100),
	username VARCHAR(20),
	passkey VARCHAR(50) NOT NULL,
	email VARCHAR(100),
	dataReg DATE,

	CONSTRAINT pk_utente PRIMARY KEY (username)
);

-- CREAZIONE TABELLA LUOGO
CREATE TABLE IF NOT EXISTS galleria_schema.luogo (
	codLuogo SERIAL,
	latitudine DOUBLE PRECISION,
	longitudine DOUBLE PRECISION,
	nomeLuogo VARCHAR(50),

	CONSTRAINT pk_luogo PRIMARY KEY (codLuogo),
	CONSTRAINT unq_nomeluogo UNIQUE(nomeLuogo),
	CONSTRAINT unq_latlon UNIQUE(latitudine, longitudine),
	CONSTRAINT chk_lat CHECK (latitudine <= 90 AND latitudine >= -90),
	CONSTRAINT chk_lon CHECK (longitudine <= 180 AND longitudine >= -180)
);

-- CREAZIONE TABELLA GALLERIA CONDIVISA
CREATE TABLE IF NOT EXISTS galleria_schema.galleria_condivisa(
	codGalleria SERIAL,
	nomeGalleria VARCHAR(50),

	CONSTRAINT pk_galleriaC PRIMARY KEY (codGalleria)
);

-- CREAZIONE TABELLA GALLERIA PERSONALE
CREATE TABLE IF NOT EXISTS galleria_schema.galleria_personale(
	codGalleria SERIAL,
	proprietario VARCHAR(20),

	CONSTRAINT pk_galleriap PRIMARY KEY (codGalleria),
	CONSTRAINT fk_galleriap FOREIGN KEY (proprietario) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT unq_prop UNIQUE (proprietario)
);

-- CREAZIONE TABELLA DISPOSITIVO
CREATE TABLE IF NOT EXISTS galleria_schema.dispositivo (
	codDisp SERIAL,
	nomeDisp VARCHAR(30),
	proprietario VARCHAR(20),

	CONSTRAINT pk_disp PRIMARY KEY (codDisp),
	CONSTRAINT fk_disputente FOREIGN KEY (proprietario) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT unq_propnomedisp UNIQUE (nomeDisp, proprietario)
);

-- CREAZIONE TABELLA SOGGETTO FOTO
CREATE TABLE IF NOT EXISTS galleria_schema.soggettofoto(
	codSogg SERIAL,
	nome VARCHAR(50) NOT NULL,
	categoria VARCHAR(100) NOT NULL,

	CONSTRAINT pk_soggettofoto PRIMARY KEY (codSogg),
	CONSTRAINT unq_nomecategoria UNIQUE (nome, categoria)
);

-- CREAZIONE TABELLA FOTO
CREATE TABLE IF NOT EXISTS galleria_schema.foto(
	codFoto SERIAL ,
	privata BOOLEAN DEFAULT FALSE NOT NULL ,
	rimossa BOOLEAN DEFAULT FALSE NOT NULL ,
	dataScatto DATE,
	codGalleriaP INTEGER,
	autoreScatto VARCHAR(20),
	dispositivo INTEGER,
	img BYTEA,

	CONSTRAINT pk_foto PRIMARY KEY (codFoto),
	CONSTRAINT fk_fotogalleria FOREIGN KEY (codGalleriaP) REFERENCES galleria_schema.galleria_personale(codGalleria) ON DELETE CASCADE,
	CONSTRAINT fk_fotoutente FOREIGN KEY (autoreScatto) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_fotodispositivo FOREIGN KEY (dispositivo) REFERENCES galleria_schema.dispositivo(codDisp) ON DELETE CASCADE
);

-- CREAZIONE TABELLA PARTECIPAZIONE UTENTI A GALLERIA CONDIVISA
CREATE TABLE IF NOT EXISTS galleria_schema.partecipazione(
	codUtente VARCHAR(20),
	codGalleriaC INTEGER,

	CONSTRAINT pk_part PRIMARY KEY (codUtente, codGalleriaC),
	CONSTRAINT fk_partutente FOREIGN KEY (codUtente) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_partfoto FOREIGN KEY (codGalleriaC) REFERENCES galleria_schema.galleria_condivisa(codGalleria) ON DELETE CASCADE
);

-- CREAZIONE TABELLA TAG
CREATE TABLE IF NOT EXISTS galleria_schema.tag (
	codFoto INTEGER,
	codUtente VARCHAR(20),

	CONSTRAINT pk_tag PRIMARY KEY (codUtente, codFoto),
	CONSTRAINT fk_tagutente FOREIGN KEY (codUtente) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_tagfoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE
);

-- CREAZIONE TABELLA PRESENZA FOTO IN GALLERIA CONDIVISA
CREATE TABLE IF NOT EXISTS galleria_schema.presenzafoto(
	codGalleriaC INTEGER,
	codFoto INTEGER,

	CONSTRAINT pk_presfoto PRIMARY KEY (codGalleriaC, codFoto),
	CONSTRAINT fk_presfgall FOREIGN KEY (codGalleriaC) REFERENCES galleria_schema.galleria_condivisa(codGalleria) ON DELETE CASCADE,
	CONSTRAINT fk_presffoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE
);

-- CREAZIONE TABELLA PRESENZA LUOGO IN FOTO
CREATE TABLE IF NOT EXISTS galleria_schema.presenzaLuogo (
    codFoto INTEGER,
    codLuogo INTEGER,

    CONSTRAINT pk_presenzaluogo PRIMARY KEY (codFoto, codLuogo),
    CONSTRAINT fk_foto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE,
    CONSTRAINT fk_luogo FOREIGN KEY (codLuogo) REFERENCES galleria_schema.luogo(codLuogo) ON DELETE CASCADE
);

-- CREAZIONE TABELLA PRESENZA SOGGETTO IN FOTO
CREATE TABLE IF NOT EXISTS galleria_schema.presenzaSoggetto (
    codFoto INTEGER,
    codSogg INTEGER,

    CONSTRAINT pk_presenzasoggetto PRIMARY KEY (codFoto, codSogg),
    CONSTRAINT fk_foto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE,
    CONSTRAINT fk_soggfoto FOREIGN KEY (codSogg) REFERENCES galleria_schema.soggettofoto(codSogg) ON DELETE CASCADE
);

-- CREAZIONE VISTA TOP 3 LUOGHI PIU' IMMORTALATI
CREATE OR REPLACE VIEW galleria_schema.top3 AS (
		SELECT * FROM
			(SELECT codluogo, COUNT(codluogo) AS numero_occorrenze
			FROM galleria_schema.presenzaluogo
			GROUP BY codluogo
			ORDER BY numero_occorrenze DESC
			LIMIT 3) as top3 NATURAL JOIN galleria_schema.luogo
);

-- FUNZIONE CHE VIENE ESEGUITA PRIMA DI OGNI INSERIMENTO DI UN RECORD NELLA TABELLA UTENTE
CREATE OR REPLACE FUNCTION galleria_schema.inserimento_utente()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.username NOT SIMILAR TO '[a-zA-Z0-9]{1,20}') THEN
		RAISE EXCEPTION 'Il campo username deve contenere solo caratteri alfanumerici e deve essere lungo al massimo 20 caratteri.';
	END IF;
    IF (NEW.passkey = '') THEN
		RAISE EXCEPTION 'Il campo password non può essere vuoto.';
	END IF;
    INSERT INTO galleria_schema.utente VALUES (NEW.nome, NEW.cognome, NEW.username, NEW.passkey, NEW.email, NEW.dataReg);
    RAISE NOTICE 'Utente % registrato correttamente.', NEW.username;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGER CHE CHIAMA LA FUNZIONE inserimento_utente PRIMA DI OGNI INSERIMENTO DI UN UTENTE
CREATE OR REPLACE TRIGGER tr_inserimento_utente
BEFORE INSERT ON galleria_schema.utente
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.inserimento_utente();

-- FUNZIONE CHE VIENE ESEGUITA DOPO OGNI INSERIMENTO DI UN UTENTE
CREATE OR REPLACE FUNCTION galleria_schema.crea_galleria_personale()
RETURNS TRIGGER AS $$
BEGIN
	INSERT INTO galleria_schema.galleria_personale VALUES (DEFAULT, NEW.username);
	RAISE NOTICE 'Galleria personale per utente % creata correttamente.', NEW.username;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGER CHE CHIAMA LA FUNZIONE crea_galleria_personale DOPO OGNI INSERIMENTO DI UN UTENTE
CREATE OR REPLACE TRIGGER tr_crea_gallp
AFTER INSERT ON galleria_schema.utente
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.crea_galleria_personale();

-- PROCEDURA CHE SOSTITUISCE L'INSERIMENTO DIRETTO DI UN LUOGO
CREATE OR REPLACE PROCEDURE galleria_schema.inserisci_luogo(
    latitudine IN DOUBLE PRECISION,
    longitudine IN DOUBLE PRECISION,
    nomeluogo IN VARCHAR(50)
) AS $$
BEGIN

    IF latitudine IS NULL AND longitudine IS NOT NULL THEN
        RAISE EXCEPTION 'Il luogo deve avere una latitudine e una longitudine.';
    ELSIF nomeluogo IS NOT NULL AND latitudine IS NOT NULL AND longitudine IS NULL THEN
        RAISE EXCEPTION 'Coordinate non valide.';
    ELSIF nomeluogo IS NULL AND longitudine IS NULL THEN
        RAISE EXCEPTION 'Il luogo deve avere un nome o delle coordinate (o entrambi).';
    END IF;

    INSERT INTO galleria_schema.luogo VALUES (DEFAULT, latitudine, longitudine, nomeluogo);
    RAISE NOTICE 'Luogo inserito con successo.';
END;
$$ LANGUAGE plpgsql;


-- TRIGGER CHE CHIAMA LA FUNZIONE coord_unique PRIMA DI OGNI INSERIMENTO DI UN LUOGO
CREATE OR REPLACE TRIGGER tr_nomeluogo_unique
BEFORE INSERT ON galleria_schema.luogo
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.nomeluogo_unique();

-- FUNZIONE CHE RESTITUISCE UN SOTTOINSIEME DELLA VIEW visualizza_foto CON TUTTE LE FOTO SCATTATE DALL'UTENTE
-- INPUT : USERNAME
-- OUTPUT : SOTTOINSIME DELLA VIEW visualizza_foto
CREATE OR REPLACE FUNCTION galleria_schema.get_galleria_personale(
    username IN text
) RETURNS SETOF galleria_schema.visualizza_foto AS $$
	BEGIN
		RETURN QUERY
		    SELECT *
			FROM  galleria_schema.visualizza_foto
			WHERE autoreScatto = username AND rimossa = FALSE;
	END;
$$ LANGUAGE plpgsql;

-- FUNZIONE CHE RESTITUISCE UNA TABELLA CON TUTTI I CODICI DELLE FOTO IN CUI È TAGGATO L'UTENTE
-- INPUT : USERNAME
CREATE OR REPLACE FUNCTION galleria_schema.get_photos_with_you(
	username in text
) RETURNS TABLE (codfoto integer, autorescatto varchar(20)) AS $$
    BEGIN
		RETURN QUERY SELECT f.codfoto, f.autorescatto
        			 FROM galleria_schema.foto AS f NATURAL JOIN galleria_schema.tag as t
        			 WHERE t.codUtente = username AND f.rimossa = FALSE;
	END;
$$ language plpgsql;

-- FUNZIONE CHE RESTITUISCE TUTTE LE FOTO CHE HANNO LO STESSO SOGGETTO
-- INPUT : CODICE DEL SOGGETTO
-- OUTPUT : TABELLA CON TUTTI I CODICI E GLI AUTORI DELLE FOTO
CREATE OR REPLACE FUNCTION galleria_schema.get_foto_da_soggetto(
	codSoggetto in integer
) RETURNS TABLE (codfoto integer, autorescatto varchar(20)) AS $$
    BEGIN
		RETURN QUERY SELECT f.codfoto, f.autorescatto
        			 FROM galleria_schema.foto AS f NATURAL JOIN galleria_schema.presenzaSoggetto as ps
        			 WHERE ps.codSogg = codSoggetto;
	END;
$$ language plpgsql;

-- FUNZIONE CHE RESTITUISCE IL CODICE DELLA GALLERIA PERSONALE DELL'UTENTE
-- INPUT : USERNAME
-- OUTPUT : CODICE DELLA GALLERIA PERSONALE
CREATE OR REPLACE FUNCTION galleria_schema.get_codg (
	username IN varchar(20)
) RETURNS INTEGER as $$
BEGIN
    RETURN query (SELECT codgalleria FROM galleria_schema.galleria_personale WHERE proprietario = username);
END;
$$ language plpgsql;

--SELECT galleria_schema.get_codg('ALBERT');
--SELECT * from galleria_schema.galleria_personale;

-- FUNZIONE CHE RESTITUISCE TUTTE LE FOTO SCATTATE NELLO STESSO LUOGO
-- INPUT : CODICE DEL LUOGO
-- OUTPUT : TABELLA CON TUTTI I CODICI E GLI AUTORI DELLE FOTO
CREATE OR REPLACE FUNCTION galleria_schema.get_foto_da_luogo(
	codL in integer
) RETURNS TABLE (codfoto integer, autorescatto varchar(20)) AS $$
    BEGIN
		RETURN QUERY SELECT f.codfoto, f.autorescatto
        			 FROM galleria_schema.foto AS f NATURAL JOIN galleria_schema.presenzaluogo as pl
        			 WHERE pl.codluogo = codL;
	END;
$$ language plpgsql;

-- FUNZIONE CHE RESTITUISCE UNA STRINGA CON TUTTI I SOGGETTI PRESENTI NELLA FOTO
-- INPUT : CODICE DELLA FOTO
-- FORMATO DELLA STRINGA DI OUTPUT : 'categoria1:soggetto1, categoria2:soggetto2, ...'
CREATE OR REPLACE FUNCTION galleria_schema.get_soggetti_from_foto(
    codF in integer
) RETURNS text AS $$
DECLARE
    soggetti text;
    cursore CURSOR FOR SELECT nome, categoria FROM galleria_schema.soggettofoto as sf
                       NATURAL JOIN galleria_schema.presenzasoggetto as ps
                       WHERE ps.codfoto = codF;
    nomeSoggetto galleria_schema.soggettofoto.nome%TYPE;
    categoriaSoggetto galleria_schema.soggettofoto.categoria%TYPE;
    BEGIN
        OPEN cursore;
        soggetti := '';
        LOOP
            FETCH cursore INTO nomeSoggetto, categoriaSoggetto ;
            EXIT WHEN NOT FOUND;
            soggetti := soggetti || categoriaSoggetto || ':' ||nomeSoggetto || ', ';
        END LOOP;
        CLOSE cursore;
        RETURN soggetti;
    END;
$$ language plpgsql;

-- FUNZIONE CHE RESTITUISCE IL NOME E IL PROPRIETARIO DI UN DISPOSITIVO
-- INPUT : CODICE DEL DISPOSITIVO
-- OUTPUT : TABELLA CON IL NOME E IL PROPRIETARIO DEL DISPOSITIVO
CREATE OR REPLACE FUNCTION galleria_schema.get_dispositivo(codice IN INTEGER)
RETURNS TABLE (nomedispositivo galleria_schema.dispositivo.nomedisp%TYPE ,
               owner galleria_schema.dispositivo.proprietario%TYPE
              ) AS $$
BEGIN
    RETURN QUERY
    SELECT nomedisp, proprietario
    FROM galleria_schema.dispositivo
    WHERE codDisp = codice;
END;
$$ LANGUAGE plpgsql;

-- FUNZIONE CHE RESTITUISCE TUTTI I DISPOSITIVI DI UN UTENTE
-- INPUT : USERNAME
-- OUTPUT : TABELLA CON IL CODICE E IL NOME DI TUTTI I DISPOSITIVI DI UN UTENTE
CREATE OR REPLACE FUNCTION galleria_schema.get_all_disp(username IN galleria_schema.utente.username%TYPE)
RETURNS TABLE (codDispositivo galleria_schema.dispositivo.codDisp%TYPE,
               nomedispositivo galleria_schema.dispositivo.nomedisp%TYPE) AS $$
BEGIN
    RETURN QUERY
        SELECT coddisp, nomedisp
        FROM galleria_schema.dispositivo
        WHERE proprietario = username;
    RETURN;
END;
$$ LANGUAGE plpgsql;

-- FUNZIONE CHE RESTITUISCE TUTTE LE FOTO SCATTATE IN UN LUOGO DAL SUO NOME
-- INPUT : NOME DEL LUOGO
-- OUTPUT : SOTTOINSIEME DELLA TABELLA FOTO
CREATE OR REPLACE FUNCTION galleria_schema.ricerca_foto_per_nome_luogo(nome_luogo IN galleria_schema.luogo.nomeLuogo%TYPE)
RETURNS SETOF galleria_schema.foto AS $$
BEGIN
    RETURN QUERY
    SELECT f.*
    FROM galleria_schema.foto f
    INNER JOIN galleria_schema.presenzaLuogo pl ON f.codFoto = pl.codFoto
    INNER JOIN galleria_schema.luogo l ON pl.codLuogo = l.codLuogo
    WHERE l.nomeLuogo = nome_luogo
      AND f.privata = false
      AND f.rimossa = false;
END;
$$ LANGUAGE plpgsql;


-- FUNZIONE CHE RESTITUISCE UNA STRINGA CON TUTTI I CODICI DEI SOGGETTI PRESENTI IN UNA FOTO
-- INPUT : CODICE DELLA FOTO
-- FORMATO DELLA STRINGA DI OUTPUT : 'codSogg1, codSogg2, ...'
CREATE OR REPLACE FUNCTION galleria_schema.get_codsogg_from_foto (
    codF in integer
) RETURNS text AS $$
    DECLARE
        codS text;
        cursore CURSOR FOR SELECT codSogg FROM galleria_schema.presenzasoggetto WHERE codfoto = codF;
        codSoggetto galleria_schema.presenzasoggetto.codsogg%TYPE;
    BEGIN
        OPEN cursore;
        codS := '';
        LOOP
            FETCH cursore INTO codSoggetto;
            EXIT WHEN NOT FOUND;
            codS := codS || codSoggetto || ', ';
        END LOOP;
        CLOSE cursore;
        RETURN codS;
    END;
$$ language plpgsql;

-- FUNZIONE CHE INSERISCE UNA FOTO NEL DATABASE E RESTITUISCE IL SUO CODICE
CREATE OR REPLACE FUNCTION galleria_schema.inserisci_foto_db(
    privata IN galleria_schema.foto.privata%TYPE,
    rimossa IN galleria_schema.foto.rimossa%TYPE,
    data_scatto IN galleria_schema.foto.datascatto%TYPE,
    cod_galleria_p IN galleria_schema.foto.codgalleriap%TYPE,
    autore IN galleria_schema.foto.autorescatto%TYPE,
    cod_dispositivo IN galleria_schema.foto.dispositivo%TYPE,
    percorso_foto IN VARCHAR(255)
) RETURNS galleria_schema.foto AS $$
DECLARE
    cod_foto INTEGER;
    bytea_data BYTEA;
BEGIN
    -- Lettura del file come BYTEA
    bytea_data := pg_read_binary_file(percorso_foto);

    -- Inserimento della foto e restituzione del codice
    INSERT INTO galleria_schema.foto
    VALUES (DEFAULT, privata, rimossa, data_scatto, cod_galleria_p, autore, cod_dispositivo, bytea_data)
    RETURNING codfoto INTO cod_foto;

    RETURN codfoto;
EXCEPTION
    WHEN OTHERS THEN
        -- In caso di errore, solleva un'eccezione
        RAISE EXCEPTION 'Error inserting photo: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;


-- FUNZIONE CHE RESTITUISCE TUTTE LE FOTO CON LO STESSO SOGGETTO
-- INPUT : CATEGORIA E NOME DEL SOGGETTO
-- OUTPUT : TABELLA CON TUTTE LE FOTO CON QUEL SOGGETTO
CREATE OR REPLACE FUNCTION galleria_schema.ricerca_foto_per_soggetto(
    categoriasogg IN galleria_schema.soggettofoto.categoria%TYPE,
    nomesogg galleria_schema.soggettofoto.nome%TYPE
    )
RETURNS SETOF galleria_schema.foto AS $$
BEGIN
    RETURN QUERY
    SELECT f.*
    FROM galleria_schema.foto f
    INNER JOIN galleria_schema.presenzaSoggetto ps ON f.codFoto = ps.codFoto
    INNER JOIN galleria_schema.soggettofoto s ON ps.codSogg = s.codSogg
    WHERE s.categoria = categoriasogg
      AND s.nome = nomesogg
      AND f.privata = false
      AND f.rimossa = false;
END;
$$ LANGUAGE plpgsql;
-- TEST : SELECT * FROM galleria_schema.ricerca_foto_per_soggetto('Paesaggi', 'Monte');

-- FUNZIONE CHE RESTITUISCE TUTTI GLI UTENTI TAGGATI IN UNA FOTO
-- INPUT : CODICE DELLA FOTO
-- OUTPUT : STRINGA CON TUTTI GLI UTENTI TAGGATI
CREATE OR REPLACE FUNCTION galleria_schema.get_tags_from_foto (
    codF in integer
) RETURNS text AS $$
    DECLARE
        tags text;
        cursore CURSOR FOR SELECT codutente FROM galleria_schema.tag WHERE codfoto = codF;
        tag galleria_schema.tag.codUtente%TYPE;
    BEGIN
        OPEN cursore;
        tags := '';
        LOOP
            FETCH cursore INTO tag;
            EXIT WHEN NOT FOUND;
            tags := tags || tag || ', ';
        END LOOP;
        CLOSE cursore;
        RETURN tags;
    END;
$$ language plpgsql;

-- VIEW PER LA VISUALIZZAZIONE DELLE FOTO
CREATE OR REPLACE VIEW galleria_schema.visualizza_foto AS (
	SELECT f.codFoto,
	       f.privata,
	       f.rimossa,
	       f.autorescatto,
	       f.dataScatto,
	       f.dispositivo,
	       l.codluogo,
	       l.nomeLuogo,
	       l.latitudine,
	       l.longitudine,
	       galleria_schema.get_codsogg_from_foto(f.codfoto) as codsoggetti,
	       galleria_schema.get_soggetti_from_foto(f.codfoto) as soggetti,
	       galleria_schema.get_tags_from_foto(f.codfoto) as tags,
	       f.img
	FROM galleria_schema.foto as f JOIN galleria_schema.dispositivo as d ON f.dispositivo = d.coddisp
	    LEFT JOIN galleria_schema.presenzaLuogo as pl ON f.codfoto = pl.codfoto
	    LEFT JOIN galleria_schema.luogo as l ON pl.codluogo = l.codluogo
);
--SELECT * FROM galleria_schema.visualizza_foto;


-- Funzione per inserire una foto e i suoi dettagli associati
CREATE OR REPLACE FUNCTION galleria_schema.inserisci_foto(
    privata IN galleria_schema.foto.privata%TYPE,
    rimossa IN galleria_schema.foto.rimossa%TYPE,
    data_scatto IN galleria_schema.foto.datascatto%TYPE,
    cod_galleria_p IN galleria_schema.foto.codgalleriap%TYPE,
    autore IN galleria_schema.foto.autorescatto%TYPE,
    cod_dispositivo IN galleria_schema.foto.dispositivo%TYPE,
    percorso_foto IN VARCHAR(255),
    codluogo IN galleria_schema.luogo.codluogo%TYPE,
    nome_luogo IN galleria_schema.luogo.nomeluogo%TYPE,
    latitudine IN galleria_schema.luogo.latitudine%TYPE,
    longitudine IN galleria_schema.luogo.longitudine%TYPE,
    codsoggetti IN TEXT,
    soggetti IN TEXT,
    tags IN TEXT
) RETURNS INTEGER AS $$
DECLARE
    cod_foto INTEGER;
    bytea_data BYTEA;
    codsoggetti_arr INT[];
    soggetti_arr TEXT[];
BEGIN
    BEGIN
        bytea_data := pg_read_binary_file(percorso_foto);
    EXCEPTION
        WHEN others THEN
            RAISE EXCEPTION 'Errore nella lettura del file. Assicurati che il percorso sia corretto.';
    END;

    -- Inserimento della foto nella tabella 'foto' e restituzione del codice
    INSERT INTO galleria_schema.foto (
        privata,
        rimossa,
        dataScatto,
        codGalleriaP,
        autoreScatto,
        dispositivo,
        img
    )
    VALUES (
        privata,
        rimossa,
        data_scatto,
        cod_galleria_p,
        autore,
        cod_dispositivo,
        bytea_data
    )
    RETURNING codFoto INTO cod_foto;

    -- Inserimento dei soggetti associati alla foto nella tabella 'soggettofoto' e nella tabella 'presenzaSoggetto'
    IF codsoggetti IS NOT NULL THEN
        -- Parsing dei codici dei soggetti
        codsoggetti_arr := string_to_array(codsoggetti, ', ')::INT[];
        IF array_length(codsoggetti_arr, 1) > 0 THEN
            FOR i IN 1..array_length(codsoggetti_arr, 1) LOOP
                INSERT INTO galleria_schema.presenzaSoggetto (codFoto, codSogg)
                VALUES (cod_foto, codsoggetti_arr[i])
                -- Se la coppia (codFoto, codSogg) è già presente, non fare nulla e salta alla prossima iterazione
                ON CONFLICT (codFoto, codSogg) DO NOTHING;
            END LOOP;
        END IF;
    END IF;

    IF soggetti IS NOT NULL THEN
        -- Parsing delle informazioni soggetti
        soggetti_arr := string_to_array(soggetti, ', ');
        IF array_length(soggetti_arr, 1) > 0 THEN
            FOR i IN 1..array_length(soggetti_arr, 1) LOOP
                DECLARE categoria_soggetto TEXT;
                DECLARE nome_soggetto TEXT;
                BEGIN
                    categoria_soggetto := split_part(soggetti_arr[i], ':', 1);
                    nome_soggetto := split_part(soggetti_arr[i], ':', 2);

                    IF categoria_soggetto IS NOT NULL AND nome_soggetto IS NOT NULL THEN
                        INSERT INTO galleria_schema.soggettofoto (nome, categoria)
                        VALUES (nome_soggetto, categoria_soggetto)
                        ON CONFLICT (nome, categoria) DO NOTHING;

                        INSERT INTO galleria_schema.presenzaSoggetto (codFoto, codSogg)
                        SELECT cod_foto, codSogg FROM galleria_schema.soggettofoto
                        WHERE nome = nome_soggetto AND categoria = categoria_soggetto;
                    END IF;
                EXCEPTION
                    WHEN others THEN
                        RAISE EXCEPTION 'Errore nell''inserimento del soggetto: %', SQLERRM;
                END;
            END LOOP;
        END IF;
    END IF;

    -- Inserimento del luogo associato alla foto nella tabella 'luogo' e nella tabella 'presenzaLuogo'
    IF codluogo IS NOT NULL THEN
        INSERT INTO galleria_schema.presenzaLuogo (codFoto, codLuogo)
        VALUES (cod_foto, codluogo)
        ON CONFLICT (codFoto, codLuogo) DO NOTHING;
    END IF;

    RAISE NOTICE 'Foto con codice % inserita con successo.', cod_foto;
    RETURN cod_foto;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'Errore nell''inserimento della foto: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;


-- TRIGGER PER L'INSERIMENTO DI UNA FOTO
CREATE OR REPLACE TRIGGER tr_inserimento_foto
INSTEAD OF INSERT ON galleria_schema.visualizza_foto
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.inserisci_foto();

-- FUNZIONE PER LA CREAZIONE DI UNA GALLERIA CONDIVISA
-- INPUT : ARRAY DI USERNAME DEI FONDATORI E NOME DELLA GALLERIA
-- OUTPUT : CODICE DELLA GALLERIA
CREATE OR REPLACE FUNCTION galleria_schema.crea_galleria_condivisa(
    fondatori VARCHAR(20)[],
    nome_galleria galleria_schema.galleria_condivisa.nomegalleria%TYPE
) RETURNS INTEGER AS $$
DECLARE
    cod_galleria INTEGER;
    fondatore galleria_schema.utente.username%TYPE;
BEGIN
    -- Controlla che ci siano almeno due fondatori
    IF array_length(fondatori, 1) < 2 THEN
        RAISE EXCEPTION 'At least two founders are required.';
    END IF;

    -- Inserisce la galleria
    INSERT INTO galleria_schema.galleria_condivisa (nomegalleria)
    VALUES (nome_galleria)
    RETURNING codGalleria INTO cod_galleria;

    -- Inserisce i fondatori
    FOREACH fondatore IN ARRAY fondatori LOOP
        INSERT INTO galleria_schema.partecipazione
        VALUES (fondatore, cod_galleria);
    END LOOP;

    -- Restituisce il codice della galleria appena creata
    RETURN cod_galleria;

    EXCEPTION WHEN OTHERS THEN
        -- In caso di errore, solleva un'eccezione
        RAISE EXCEPTION 'Error creating shared gallery: %', SQLERRM;

END;
$$ LANGUAGE plpgsql;

/* TEST : SELECT * from galleria_schema.crea_galleria_condivisa(
          ARRAY['ALBERT', 'MAHATMA']::VARCHAR(20)[],
          'Example Gallery'::varchar(50));
*/

-- FUNZIONE CHE RESTITUISCE TUTTE LE GALLERIE CONDIVISE A CUI PARTECIPA UN UTENTE
-- INPUT : USERNAME DELL'UTENTE
-- OUTPUT : TABELLA CON LE GALLERIE CONDIVISE
CREATE OR REPLACE FUNCTION galleria_schema.get_gallerie_condivise(
    username galleria_schema.utente.username%TYPE
) RETURNS SETOF galleria_schema.galleria_condivisa AS $$
    BEGIN
    RETURN QUERY
    SELECT g.*
    FROM galleria_schema.galleria_condivisa g
    INNER JOIN galleria_schema.partecipazione p ON g.codgalleria = p.codGalleriaC
    WHERE p.codUtente = username;
END;
$$ LANGUAGE plpgsql;

-- VIEW PER LA VISUALIZZAZIONE DELLE GALLERIE CONDIVISE CON I PARTCIPANTI
CREATE OR REPLACE VIEW galleria_schema.galleria_partecipanti AS
(
    SELECT g.codgalleria, g.nomegalleria, array_to_string(array_agg(p.username), ', ') as partecipanti
    FROM galleria_schema.galleria_condivisa g
             INNER JOIN galleria_schema.partecipazione pa ON g.codgalleria = pa.codGalleriaC
             INNER JOIN galleria_schema.utente p ON pa.codUtente = p.username
    GROUP BY g.codgalleria
);
-- TEST : SELECT * FROM galleria_schema.galleria_partecipanti;

-- PROCEDURA UTILE PER CANCELLARE I RECORD DI TUTTE LE TABELLE
create or replace procedure galleria_schema.flush_all_tables() as $$
    begin
        truncate table galleria_schema.presenzasoggetto cascade ;
        truncate table galleria_schema.presenzaluogo cascade ;
        truncate table galleria_schema.contenuto cascade ;
        truncate table galleria_schema.tag cascade;
        truncate table galleria_schema.presenzafoto cascade;
        truncate table galleria_schema.partecipazione cascade;
        truncate table galleria_schema.foto cascade;
        truncate table galleria_schema.galleria_condivisa cascade;
        truncate table galleria_schema.galleria_personale cascade;
        truncate table galleria_schema.dispositivo cascade;
        truncate table galleria_schema.soggettofoto cascade;
        truncate table galleria_schema.luogo cascade;
        truncate table galleria_schema.utente cascade;
    end;
 $$ language plpgsql;

-- Popolamento tabella utente
INSERT INTO galleria_schema.utente (nome, cognome, username, passkey, email, datareg)
VALUES
('Leonardo', 'da Vinci', 'LEONARDO', '123456', 'leonardo@example.com', current_date),
('Albert', 'Einstein', 'ALBERT', '123456', 'albert@example.com', current_date),
('Galileo', 'Galilei', 'GALILEO', '123456', 'galileo@example.com', current_date),
('Isaac', 'Newton', 'ISAAC', '123456', 'isaac@example.com', current_date),
('Wolfgang Amadeus', 'Mozart', 'MOZART', '123456', 'mozart@example.com', current_date),
('William', 'Shakespeare', 'SHAKESPEARE', '123456', 'shakespeare@example.com', current_date),
('Nelson', 'Mandela', 'NELSON', '123456', 'nelson@example.com', current_date),
('Mahatma', 'Gandhi', 'MAHATMA', '123456', 'mahatma@example.com', current_date),
('Cleopatra', 'VII', 'CLEOPATRA', '123456', 'cleopatra@example.com', current_date),
('Julius', 'Caesar', 'CAESAR', '123456', 'caesar@example.com', current_date),
('Alexander', 'the Great', 'ALEXANDER', '123456', 'alexander@example.com', current_date),
('Joan', 'of Arc', 'JOAN', '123456', 'joan@example.com', current_date),
('George', 'Washington', 'WASHINGTON', '123456', 'washington@example.com', current_date),
('Napoleon', 'Bonaparte', 'NAPOLEON', '123456', 'napoleon@example.com', current_date),
('Abraham', 'Lincoln', 'LINCOLN', '123456', 'lincoln@example.com', current_date),
('Queen', 'Victoria', 'VICTORIA', '123456', 'victoria@example.com', current_date),
('Martin', 'Luther King Jr.', 'MARTIN', '123456', 'martin@example.com', current_date),
('Winston', 'Churchill', 'CHURCHILL', '123456', 'churchill@example.com', current_date),
('Pablo', 'Picasso', 'PICASSO', '123456', 'picasso@example.com', current_date),
('Vincent', 'van Gogh', 'VANGOGH', '123456', 'vangogh@example.com', current_date);

-- Popolamento tabella soggettofoto
INSERT INTO galleria_schema.soggettofoto (nome, categoria)
VALUES
('Monte', 'Paesaggi'),
('Spiaggia', 'Paesaggi'),
('Parco', 'Paesaggi'),
('Gran Premio d''Italia', 'Eventi sportivi'),
('Partita di calcio', 'Eventi sportivi'),
('Podio olimpico', 'Eventi sportivi'),
('Amici in piazza', 'Gruppi di persone'),
('Compleanno di Alice', 'Gruppi di persone'),
('Matrimonio di Marco e Chiara', 'Matrimoni'),
('Ritratto di Maria', 'Ritratti'),
('Selfie a New York', 'Selfie'),
('Cane Carlino', 'Animali'),
('Gatto certosino', 'Animali'),
('Tigre siberiana', 'Animali'),
('Pizza margherita', 'Cibo'),
('Spaghetti alla carbonara', 'Cibo'),
('Bistecca fiorentina', 'Cibo'),
('Cammino di Santiago', 'Viaggi'),
('Safari in Tanzania', 'Viaggi'),
('Spiaggia di Copacabana', 'Viaggi'),
('Montagna nel Cilento', 'Natura'),
('Cascata del Niagara', 'Natura'),
('Parco del Gran Paradiso', 'Natura'),
('Opera d''arte', 'Altro'),
('Costruzione in acciaio', 'Altro');

-- Popolamento tabella luogo
INSERT INTO galleria_schema.luogo (latitudine, longitudine, nomeLuogo)
VALUES
(45.8325, 6.8642, 'Monte Bianco'),
(44.0575, 12.5633, 'Rimini'),
(45.5844, 9.2731, 'Monza'),
(45.6031, 9.2646, 'Autodromo Nazionale di Monza'),
(41.8902, 12.4922, 'Stadio Olimpico di Roma'),
(40.6134, 14.5350, 'Sorrento'),
(43.8099, 11.2554, 'Firenze'),
(40.7589, -73.9851, 'New York'),
(43.6247, 13.5172, 'Fano'),
(42.7783, 11.1188, 'Monte Amiata'),
(37.8055, -122.4100, 'San Francisco'),
(37.8210, -122.4229, 'Golden Gate Park'),
(40.7580, -73.9855, 'Times Square');

-- Creazione utente dell'applicativo
CREATE USER applicativo_user WITH PASSWORD 'password';

-- Assegnazione permessi utente dell'applicativo
GRANT CONNECT ON DATABASE "Galleria" TO applicativo_user;
GRANT USAGE ON SCHEMA galleria_schema TO applicativo_user;

GRANT SELECT ON ALL TABLES IN SCHEMA galleria_schema TO applicativo_user;
GRANT INSERT ON galleria_schema.utente TO applicativo_user;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA galleria_schema TO applicativo_user;
GRANT EXECUTE ON ALL PROCEDURES IN SCHEMA galleria_schema TO applicativo_user;
