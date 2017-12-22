--Add foreing key to family table
  ALTER TABLE data_collect.snapshots_economics ADD CONSTRAINT fk_family_snapshots_economics FOREIGN KEY (family_id)
        REFERENCES ps_families.family (family_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
