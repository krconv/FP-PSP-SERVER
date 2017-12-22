ALTER TABLE data_collect.snapshots_economics
    ADD COLUMN family_country character varying (50);

ALTER TABLE data_collect.snapshots_economics
    ADD COLUMN family_city character varying (50);
