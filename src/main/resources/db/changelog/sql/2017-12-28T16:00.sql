ALTER TABLE ps_network.organizations DROP CONSTRAINT fk_organization_country_country_id,
  ADD CONSTRAINT fk_organization_country_country_id FOREIGN KEY (country)
        REFERENCES system.countries (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;