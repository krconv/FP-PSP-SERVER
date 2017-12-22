
ALTER TABLE ps_families.person RENAME name to first_name;

ALTER TABLE ps_families.person RENAME lastname to last_name;

ALTER TABLE ps_families.person RENAME country to country_of_birth;

ALTER TABLE ps_families.person
    ADD COLUMN birthdate timestamp without time zone;


