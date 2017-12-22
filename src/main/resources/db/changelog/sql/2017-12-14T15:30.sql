--This script create code column
ALTER TABLE system.countries 
    ADD COLUMN numeric_code CHAR(3);

ALTER TABLE system.countries 
    ADD COLUMN alfa_3_code CHAR(3);

ALTER TABLE system.countries 
    ADD COLUMN alfa_2_code CHAR(2);


