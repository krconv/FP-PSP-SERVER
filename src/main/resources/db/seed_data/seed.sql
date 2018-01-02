delete from data_collect.snapshots_properties_attributes;

-- economic required and recommended survey properties
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('areaOfResidence', 'areaOfResidence', 'MANDATORY', 'es', 'Ingrese su área de residencia', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('employmentStatusPrimary', 'employmentStatusPrimary','MANDATORY', 'es', 'Ingrese su puesto en su empleo principal', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('employmentStatusSecondary', 'employmentStatusSecondary', 'MANDATORY', 'es', 'Ingrese su puesto en su empleo secundario', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('currency', 'currency', 'MANDATORY', 'es', 'Ingrese su moneda', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('activityMain', 'activityMain', 'MANDATORY', 'es', 'Ingrese su actividad primaria', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('activitySecondary', 'activitySecondary', 'MANDATORY', 'es', 'Ingrese su actividad secundaria', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('householdMonthlyIncome', 'householdMonthlyIncome', 'MANDATORY', 'es', 'Ingrese su ingreso mensual en su hogar', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('salaryIncome', 'salaryIncome', 'MANDATORY', 'es', 'Ingrese su salario mensual', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('benefitIncome', 'benefitIncome', 'MANDATORY', 'es', 'Ingrese su ingreso a modo de beneficio', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('pensionIncome', 'pensionIncome', 'MANDATORY', 'es', 'Ingrese su pensión', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('savingsIncome', 'savingsIncome', 'MANDATORY', 'es', 'Ingrese su ingreso por medio de ahorros', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('otherIncome', 'otherIncome', 'MANDATORY', 'es', 'Ingrese otros ingresos que tenga', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('householdMonthlyOutgoing', 'householdMonthlyOutgoing', 'MANDATORY', 'es', 'Ingrese los gastos mensuales que tiene en su hogar', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('netSuplus', 'netSuplus', 'MANDATORY', 'es', 'Ingrese net SUPLUS?', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('educationClientLevel', 'educationClientLevel', 'MANDATORY', 'es', 'Nivel de educación del entrevistado', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('educationPersonMostStudied', 'educationPersonMostStudied', 'MANDATORY', 'es', 'Nivel de educación de la persona con más estudios en la familia', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('educationLevelAttained', 'educationLevelAttained', 'MANDATORY', 'es', 'Nivel de educación alcanzado', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('housingSituation', 'housingSituation', 'MANDATORY', 'es', 'Situación de la casa', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('familyUbication', 'familyUbication', 'MANDATORY', 'es', 'Seleccione la ubicación geográfica de la familia', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('familyCountry', 'familyCountry', 'MANDATORY', 'es', 'País en el que recide la familia', 'ECONOMIC');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('familyCity', 'familyCity', 'MANDATORY', 'es', 'Ciudad en la que recide la familia', 'ECONOMIC');


-- indicator required and recommended survey properties
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('drinkingWaterAccess', 'drinkingWaterAccess', 'MANDATORY', 'es', 'Usted cuenta con acceso a agua potable?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('nearbyHealthPost', 'nearbyHealthPost',  'MANDATORY', 'es', 'Usted cuenta con algún puesto de salud cercano?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('income', 'income',  'MANDATORY', 'es', 'Esto representa su ingreso?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('documentation', 'documentation',  'MANDATORY', 'es', 'Ingrese su documentación', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('alimentation', 'alimentation',  'MANDATORY', 'es', 'Puede alimentarse todos los días?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('garbageDisposal', 'garbageDisposal', 'MANDATORY', 'es', 'Cuenta con posibilidad de deshacerse de la basura?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('safeHouse', 'safeHouse', 'MANDATORY', 'es', 'Cuenta con una casa segura?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('safeBathroom', 'safeBathroom', 'MANDATORY', 'es', 'Cuenta con baño seguro?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('electricityAccess', 'electricityAccess', 'MANDATORY', 'es', 'Cuenta con acceso a electricidad?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('refrigerator', 'refrigerator', 'MANDATORY', 'es', 'Cuenta con una heladera?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('separateBed', 'separateBed', 'MANDATORY', 'es', 'Cuenta con una cama separada?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('separateBedrooms', 'separateBedrooms', 'MANDATORY', 'es', 'Cuenta con cuartos separados?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('properKitchen', 'properKitchen', 'MANDATORY', 'es', 'Cuenta con cuartos separados?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('phone', 'phone', 'MANDATORY', 'es', 'Ingrese su número de teléfono', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('security', 'security', 'MANDATORY', 'es', 'Cuenta con una casa segura?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('readAndWrite', 'readAndWrite', 'MANDATORY', 'es', 'Puede leer y escribir?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('middleEducation', 'middleEducation', 'MANDATORY', 'es', 'Tuvo acceso a educación media?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('socialCapital', 'socialCapital', 'MANDATORY', 'es', 'Cuenta con capital social?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('informationAccess', 'informationAccess', 'MANDATORY', 'es', 'Ingrese información de acceso', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('influenceInPublicSector', 'influenceInPublicSector', 'MANDATORY', 'es', 'Tiene influencia en el sector público?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('awarenessOfNeeds', 'awarenessOfNeeds', 'MANDATORY', 'es', 'Tiene consciencia de sus necesidades?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('selfEsteem', 'selfEsteem', 'MANDATORY', 'es', 'Nivel de autoestima?', 'INDICATOR');
INSERT INTO data_collect.snapshots_properties_attributes(
	property_system_name, property_schema_name, stoplight_type, lang, title, stoplight_group)
	VALUES ('autonomyDecisions', 'autonomyDecisions', 'MANDATORY', 'es', 'Puede tomar decisiones de forma autónoma?', 'INDICATOR');




