INSERT INTO employee (id, created, updated, version, date_of_birth, driving_status, first_name, gender, last_name, phone_number, status)
SELECT NEXTVAL('employee_id_sequence'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '1999-03-20', 0, 'Akibul', 'MALE', 'Doe', '01815980623', 'ACTIVE';

INSERT INTO auth_info (id, created, updated, version, email, hased_password, salt, employee_id)
SELECT NEXTVAL('auth_id_sequence'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'admin@gmail.com', '63311eff390b3c6432c5a2dcf466b99299bba61c28a4cc4fa080c1c1ca4457c1', 'a118915d123da3c7e1e970ad47e18c8b', 1;
-- email:admin@gmail.com, pass: admin123

INSERT INTO employee_types (id, employee_type)
VALUES (1, 'SUPER_ADMIN'), (1, 'ADMIN');