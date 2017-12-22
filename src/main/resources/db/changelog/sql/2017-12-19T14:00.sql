
INSERT INTO security.users (username, pass, active)
    VALUES ('partner_admin', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);

INSERT INTO security.users (username, pass, active)
  VALUES ('survey_user', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);

INSERT INTO security.users_roles (user_id, role)
  VALUES (
    (SELECT id FROM security.users WHERE username = 'partner_admin'),
    'ROLE_APP_ADMIN');
INSERT INTO security.users_roles (user_id, role)
  VALUES (
  (SELECT id FROM security.users WHERE username = 'survey_user'),
  'ROLE_SURVEY_USER');


-- new organization for partner demo only
INSERT INTO ps_network.organizations (name, code, description, is_active, country, information, application_id)
  VALUES ('Partner',
  'partnerorg',
  'Partner',
  true,
  (SELECT id FROM system.countries where country = 'Paraguay'),
  NULL,
  (SELECT id FROM ps_network.applications WHERE code = 'fpapp'));


INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'partner_admin'),
	  (SELECT id from ps_network.applications WHERE code = 'fpapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'partnerorg'));

INSERT INTO ps_network.users_applications(user_id, application_id, organization_id)
	VALUES (
	  (SELECT id from security.users WHERE username = 'survey_user'),
	  (SELECT id from ps_network.applications WHERE code = 'fpapp'),
	  (SELECT id from ps_network.organizations WHERE code = 'fporg'));
