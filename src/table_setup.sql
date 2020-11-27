DROP DATABASE IF EXISTS school;
CREATE DATABASE IF NOT EXISTS school;
SET foreign_key_checks = 0;

CREATE TABLE IF NOT EXISTS school.instructor (
 instructor_id INT AUTO_INCREMENT PRIMARY KEY,
 first_name VARCHAR(255) NOT NULL,
 last_name VARCHAR(255) NOT NULL,
 social_security_number VARCHAR(12) NOT NULL,
 age INT,
 city VARCHAR(255),
 zip_code VARCHAR(5),
 street_name VARCHAR(255),
 phone_number VARCHAR(10) NOT NULL,
 home_number VARCHAR(10),
 email VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS school.instructor_payment (
 instructor_id INT,
 due_date DATE NOT NULL,
 total_income DOUBLE PRECISION(6,2) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS school.lesson (
 lesson_id INT AUTO_INCREMENT PRIMARY KEY,
 schedule_time TIMESTAMP(6) NOT NULL,
 lesson_type NUMERIC(3) NOT NULL,
 type_cost DOUBLE PRECISION(6,2),
 day_cost DOUBLE PRECISION(6,2),
 level_cost DOUBLE PRECISION(6,2),
 total_cost DOUBLE PRECISION(6,2),
 minimum_enrollment INT NOT NULL,
 maximum_enrollment INT,
 genre VARCHAR(255),
 instructor_id INT
);


CREATE TABLE IF NOT EXISTS school.student (
 student_id INT AUTO_INCREMENT PRIMARY KEY,
 first_name VARCHAR(255) NOT NULL,
 last_name VARCHAR(255) NOT NULL,
 age INT,
 city VARCHAR(255),
 zip_code VARCHAR(5),
 street_name VARCHAR(255),
 social_security_number VARCHAR(12) NOT NULL
);



CREATE TABLE IF NOT EXISTS school.tutor_skill (
 instructor_id INT PRIMARY KEY,
 instrument VARCHAR(255) NOT NULL,
 instrument_level NUMERIC(3)
);



CREATE TABLE IF NOT EXISTS school.contact_detail (
 student_id INT PRIMARY KEY,
 phone_number VARCHAR(10) NOT NULL,
 home_number VARCHAR(10),
 email VARCHAR(255) NOT NULL
);



CREATE TABLE IF NOT EXISTS school.ensemble_skill (
 instructor_id INT PRIMARY KEY,
 ensemble_genre VARCHAR(255)
);



CREATE TABLE IF NOT EXISTS school.invoice (
 student_id INT PRIMARY KEY,
 total_cost VARCHAR(255) NOT NULL,
 due_date DATE NOT NULL,
 discount VARCHAR(255),
 created_at VARCHAR(255) NOT NULL
);



CREATE TABLE IF NOT EXISTS school.parent (
 student_id INT PRIMARY KEY,
 first_name VARCHAR(255),
 last_name VARCHAR(255),
 phone_number VARCHAR(10) NOT NULL,
 email VARCHAR(255) NOT NULL
);



CREATE TABLE IF NOT EXISTS school.rental (
 rental_id INT AUTO_INCREMENT PRIMARY KEY,
 student_id INT,
 city VARCHAR(255),
 zip_code VARCHAR(5),
 street_name VARCHAR(255),
 country VARCHAR(255),
 rental_due_date TIMESTAMP(6),
 total_cost DOUBLE PRECISION(6,2)
);



CREATE TABLE IF NOT EXISTS school.skill (
 skill_id INT AUTO_INCREMENT PRIMARY KEY,
 student_id INT,
 instrument VARCHAR(255) NOT NULL,
 instrument_level NUMERIC(3) NOT NULL,
 passed_audition BIT(1),
 created_at TIMESTAMP(6) NOT NULL,
 updated_at TIMESTAMP(6)
);



CREATE TABLE IF NOT EXISTS school.application (
 student_id INT PRIMARY KEY,
 save_application BIT(1) NOT NULL,
 contactable BIT(1) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL,
 updated_at TIMESTAMP(6),
 skill_id INT,
 lesson_id INT,
 age INT
);



CREATE TABLE IF NOT EXISTS school.instrument_rental (
 rental_id INT AUTO_INCREMENT PRIMARY KEY,
 student_id INT,
 instrument VARCHAR(255),
 instrument_type VARCHAR(255),
 instrument_cost DOUBLE PRECISION(6,2)
);


ALTER TABLE school.instructor_payment ADD CONSTRAINT FK_instructor_payment_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE school.lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE school.lesson ADD CONSTRAINT FK_lesson_1 FOREIGN KEY (instructor_id) REFERENCES instructor_payment (instructor_id);


ALTER TABLE school.tutor_skill ADD CONSTRAINT FK_tutor_skill_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE school.contact_detail ADD CONSTRAINT FK_contact_detail_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE school.ensemble_skill ADD CONSTRAINT FK_ensemble_skill_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE school.invoice ADD CONSTRAINT FK_invoice_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE school.parent ADD CONSTRAINT FK_parent_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE school.rental ADD CONSTRAINT FK_rental_0 FOREIGN KEY (student_id) REFERENCES invoice (student_id);


ALTER TABLE school.skill ADD CONSTRAINT FK_skill_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE school.application ADD CONSTRAINT FK_application_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE school.application ADD CONSTRAINT FK_application_1 FOREIGN KEY (student_id) REFERENCES invoice (student_id);
ALTER TABLE school.application ADD CONSTRAINT FK_application_2 FOREIGN KEY (skill_id) REFERENCES skill (skill_id);
ALTER TABLE school.application ADD CONSTRAINT FK_application_3 FOREIGN KEY (student_id) REFERENCES skill (student_id);
ALTER TABLE school.application ADD CONSTRAINT FK_application_4 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE school.instrument_rental ADD CONSTRAINT FK_instrument_rental_0 FOREIGN KEY (rental_id) REFERENCES rental (rental_id);
ALTER TABLE school.instrument_rental ADD CONSTRAINT FK_instrument_rental_1 FOREIGN KEY (student_id) REFERENCES rental (student_id);


ALTER TABLE school.application ADD CONSTRAINT FK_application_6 CHECK (student_id.age >= 16);