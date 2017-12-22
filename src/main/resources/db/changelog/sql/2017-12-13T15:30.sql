--This script create column for store family code
ALTER TABLE ps_families.family
    ADD COLUMN code character varying (255) NOT NULL ;
