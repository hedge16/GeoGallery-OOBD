DROP SCHEMA galleria_schema CASCADE; 
CREATE SCHEMA galleria_schema;
CREATE SCHEMA IF NOT EXISTS galleria_schema;

CREATE TABLE IF NOT EXISTS galleria_schema.utente (
	nome VARCHAR(100),
	cognome VARCHAR(100),
	username VARCHAR(20),
	passkey VARCHAR(50) NOT NULL,
	email VARCHAR(100),
	dataReg DATE,
	CONSTRAINT pk_utente PRIMARY KEY (username)

);

CREATE TABLE IF NOT EXISTS galleria_schema.luogo (
	codLuogo SERIAL,
	latitudine DOUBLE PRECISION,
	longitudine DOUBLE PRECISION,
	nomeLuogo VARCHAR(50),
	CONSTRAINT pk_luogo PRIMARY KEY (codLuogo),
	CONSTRAINT unq_luogo UNIQUE(nomeLuogo, latitudine, longitudine),
	CONSTRAINT chk_lat CHECK (latitudine <= 90 AND latitudine >= -90),
	CONSTRAINT chk_lon CHECK (longitudine <= 180 AND longitudine >= -180)
);

CREATE TABLE IF NOT EXISTS galleria_schema.galleria_condivisa(
	codGalleria SERIAL,
	nomeGalleria VARCHAR(50),
	CONSTRAINT pk_galleriaC PRIMARY KEY (codGalleria)

);

CREATE TABLE IF NOT EXISTS galleria_schema.galleria_personale(
	codGalleria SERIAL,
	proprietario VARCHAR(20),
	CONSTRAINT pk_galleriap PRIMARY KEY (codGalleria),
	CONSTRAINT fk_galleriap FOREIGN KEY (proprietario) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE

);




CREATE TABLE IF NOT EXISTS galleria_schema.dispositivo (
	codDisp SERIAL,
	nomeDisp VARCHAR(30),
	proprietario VARCHAR(20),
	CONSTRAINT pk_disp PRIMARY KEY (codDisp),
	CONSTRAINT fk_disputente FOREIGN KEY (proprietario) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT unq_propnomedisp UNIQUE (nomeDisp, proprietario)

);


CREATE TABLE IF NOT EXISTS galleria_schema.soggettofoto(
	codSogg SERIAL,
	nome VARCHAR(50),
	categoria VARCHAR(100),
	CONSTRAINT pk_soggettofoto PRIMARY KEY (codSogg)

);

CREATE TABLE IF NOT EXISTS galleria_schema.foto(
	codFoto SERIAL ,
	privata BOOLEAN NOT NULL ,
	rimossa BOOLEAN NOT NULL ,
	dataScatto DATE,
	codGalleriaP INTEGER,
	autoreScatto VARCHAR(20),
	dispositivo INTEGER,
	img BYTEA NOT NULL,
	CONSTRAINT pk_foto PRIMARY KEY (codFoto),
	CONSTRAINT fk_fotogalleria FOREIGN KEY (codGalleriaP) REFERENCES galleria_schema.galleria_personale(codGalleria) ON DELETE CASCADE,
	CONSTRAINT fk_fotoutente FOREIGN KEY (autoreScatto) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_fotodispositivo FOREIGN KEY (dispositivo) REFERENCES galleria_schema.dispositivo(codDisp)
);

CREATE TABLE IF NOT EXISTS galleria_schema.partecipazione(

	codUtente VARCHAR(20),
	codGalleriaC INTEGER,
	CONSTRAINT pk_part PRIMARY KEY (codUtente, codGalleriaC),
	CONSTRAINT fk_partutente FOREIGN KEY (codUtente) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_partfoto FOREIGN KEY (codGalleriaC) REFERENCES galleria_schema.galleria_condivisa(codGalleria) ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS galleria_schema.tag (

	codFoto INTEGER NOT NULL,
	codUtente VARCHAR(20) NOT NULL,
	CONSTRAINT pk_tag PRIMARY KEY (codUtente, codFoto),
	CONSTRAINT fk_tagutente FOREIGN KEY (codUtente) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_tagtfoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS galleria_schema.presenzafoto(

	codGalleriaC INTEGER NOT NULL,
	codFoto INTEGER NOT NULL,
	CONSTRAINT pk_presfoto PRIMARY KEY (codGalleriaC, codFoto),
	CONSTRAINT fk_presfgall FOREIGN KEY (codGalleriaC) REFERENCES galleria_schema.galleria_condivisa(codGalleria) ON DELETE CASCADE,
	CONSTRAINT fk_presffoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE
	
);

CREATE TABLE IF NOT EXISTS galleria_schema.contenuto (
	soggetto INTEGER,
	codFoto INTEGER,
	CONSTRAINT pk_contenuto PRIMARY KEY (soggetto, codFoto),
	CONSTRAINT fk_contsog FOREIGN KEY (soggetto) REFERENCES galleria_schema.soggettofoto(codSogg) ON DELETE CASCADE,
	CONSTRAINT fk_contfoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS galleria_schema.presenzaLuogo (
    codFoto INTEGER,
    codLuogo INTEGER,
    CONSTRAINT fk_foto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE,
    CONSTRAINT fk_luogo FOREIGN KEY (codLuogo) REFERENCES galleria_schema.luogo(codLuogo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS galleria_schema.presenzaSoggetto (
    codFoto INTEGER,
    codSogg INTEGER,
    CONSTRAINT fk_foto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE,
    CONSTRAINT fk_luogo FOREIGN KEY (codSogg) REFERENCES galleria_schema.soggettofoto(codSogg) ON DELETE CASCADE
);



CREATE VIEW galleria_schema.TOP3 AS (
	SELECT nomeLuogo, codLuogo
	FROM galleria_schema.luogo 
	WHERE codLuogo IN (
		SELECT codLuogo 
		FROM galleria_schema.foto 
		GROUP BY codLuogo
		ORDER BY COUNT(codLuogo)
		LIMIT 3
		)
	
	);

CREATE OR REPLACE FUNCTION galleria_schema.nomeluogo_unique ()
RETURNS trigger AS $$
DECLARE 
    scansiona_luogo CURSOR FOR SELECT nomeluogo FROM galleria_schema.luogo;
    nomeLuogo galleria_schema.luogo.nomeluogo%TYPE;
    isNotUnique BOOLEAN := FALSE;
BEGIN
	IF (NEW.nomeluogo IS NOT NULL) THEN
	    OPEN scansiona_luogo;
	    LOOP	    	
	        FETCH scansiona_luogo INTO nomeLuogo;     
	        EXIT WHEN NOT FOUND OR isNotUnique = TRUE;
	        IF nomeLuogo = NEW.nomeluogo THEN
	            isNotUnique := TRUE;
	        END IF;
	    END LOOP;
	    CLOSE scansiona_luogo;
	    IF isNotUnique THEN 
	        RAISE EXCEPTION 'Un luogo con lo stesso nome è già presente nel sistema.';
	    END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION galleria_schema.coord_unique ()
RETURNS trigger AS $$
DECLARE 
    scansiona_luogo CURSOR FOR SELECT latitudine, longitudine FROM galleria_schema.luogo;
    lat DOUBLE PRECISION;
    long DOUBLE PRECISION;
    isNotUnique BOOLEAN := FALSE;
BEGIN
	IF (NEW.latitudine IS NOT NULL AND NEW.longitudine IS NOT NULL) THEN
	    OPEN scansiona_luogo;
	    LOOP
	        FETCH scansiona_luogo INTO lat, long;        
			EXIT WHEN NOT FOUND OR isNotUnique = TRUE;
	        IF NEW.latitudine = lat AND NEW.longitudine = long THEN
	            isNotUnique := TRUE;
	        END IF;
	    END LOOP;
	    CLOSE scansiona_luogo;
	    IF isNotUnique THEN 
	        RAISE EXCEPTION 'Un luogo con le stesse coordinate è già presente nel sistema.';
	    END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER tr_coord_unique
BEFORE INSERT ON galleria_schema.luogo
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.coord_unique();

CREATE OR REPLACE TRIGGER tr_nomeluogo_unique
BEFORE INSERT ON galleria_schema.luogo
FOR EACH ROW
EXECUTE FUNCTION galleria_schema.nomeluogo_unique();



