CREATE TYPE translation_part_type AS ENUM (
	'SONG',
	'ADERTISE',
	'INTERVIEW'
);


CREATE TABLE IF NOT EXISTS dj(
	dj_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(255) NOT NULL,
	experience VARCHAR(255) NOT NULL,
	resume VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS translations(
	translation_id SERIAL PRIMARY KEY,
	dj_id INT NOT NULL,
	duration INT NOT NULL,
	title VARCHAR(255) NOT NULL,
	FOREIGN KEY (dj_id) REFERENCES dj(dj_id)
);

CREATE TABLE IF NOT EXISTS translation_parts(
	translation_part_id SERIAL PRIMARY KEY,
	type varchar (100) NOT NULL,
	attribute varchar(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS translation_to_part(
    translation_id INT NOT NULL,
    translation_part_id INT NOT NULL,
	FOREIGN KEY (translation_id) REFERENCES translations(translation_id),
	FOREIGN KEY (translation_part_id) REFERENCES translation_parts(translation_part_id)
);