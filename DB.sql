/* DROP SCHEMA galleria_schema CASCADE; */
CREATE SCHEMA galleria_schema;
CREATE TYPE galleria_schema.categoria_soggetto AS ENUM ('Paesaggi', 'Eventi sportivi', 'Gruppi di persone', 'Ritratti', 'Selfie', 'Animali', 'Cibo', 'Matrimoni', 'Viaggi', 'Natura');
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
	CONSTRAINT unq_luogo UNIQUE(nomeLuogo, latitudine, longitudine)
	
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
	categoria galleria_schema.categoria_soggetto,
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
	codLuogo INTEGER,
	CONSTRAINT pk_foto PRIMARY KEY (codFoto),
	CONSTRAINT fk_fotogalleria FOREIGN KEY (codGalleriaP) REFERENCES galleria_schema.galleria_personale(codGalleria) ON DELETE CASCADE,
	CONSTRAINT fk_fotoutente FOREIGN KEY (autoreScatto) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_fotodispositivo FOREIGN KEY (dispositivo) REFERENCES galleria_schema.dispositivo(codDisp),
	CONSTRAINT fk_fotoluogo FOREIGN KEY (codLuogo) REFERENCES galleria_schema.luogo(codLuogo)
	
	
);

CREATE TABLE IF NOT EXISTS galleria_schema.partecipazione(

	codUtente VARCHAR(20),
	codFoto INTEGER,
	CONSTRAINT pk_part PRIMARY KEY (codUtente, codFoto),
	CONSTRAINT fk_partutente FOREIGN KEY (codUtente) REFERENCES galleria_schema.utente(username) ON DELETE CASCADE,
	CONSTRAINT fk_partfoto FOREIGN KEY (codFoto) REFERENCES galleria_schema.foto(codFoto) ON DELETE CASCADE


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














