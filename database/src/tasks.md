# TASK 3 - Queries and Data Serialization  

## Analysis report

### Show the number of instruments rented per month during a specified year.

Create a view  
``` sql
CREATE OR REPLACE VIEW rented_instruments AS SELECT rental_due_date AS due_date, instrument FROM instrument_rental WHERE rental_id IS NOT NULL;  
SELECT YEAR(due_date), MONTH(due_date), instrument, COUNT(instrument) FROM rented_instruments GROUP BY due_date, instrument;
```

### Show the total number of rented instruments.
``` sql
CREATE OR REPLACE VIEW rented_instruments AS SELECT rental_due_date AS due_date, instrument, instrument_type AS type FROM instrument_rental WHERE rental_id IS NOT NULL;  
SELECT COUNT(instrument) FROM rented_instruments;
```

### Show the number of rents per instrument.

``` sql
CREATE OR REPLACE VIEW rented_instruments AS SELECT rental_due_date AS due_date, instrument, instrument_type AS type FROM instrument_rental WHERE rental_id IS NOT NULL;  
SELECT instrument, type, COUNT(instrument) FROM rented_instruments WHERE due_date LIKE "2020-12%" GROUP BY instrument,type;
```

### Show the average number of instruments rented per month during a specified  year.
``` sql
SELECT ins_rental.month, total_number / 12 AS average FROM (SELECT Month(rental_due_date) AS month, COUNT(*) AS total_number FROM instrument_rental WHERE rental_id IS NOT NULL GROUP BY Month(rental_due_date)) AS ins_rental GROUP BY month;
```

### Show the number of lesson given per month during a specified year.
``` sql
SELECT DISTINCT Month(start_time), COUNT(DISTINCT start_time) as total_number FROM lesson GROUP BY Month(start_time) ORDER BY Month(start_time);
```
### Show the total number of lessons.
``` sql
SELECT COUNT(DISTINCT start_time) FROM lesson;
```
### Show the total number of lesson per type.
``` sql
SELECT DISTINCT Month(start_time),type_cost, COUNT(DISTINCT start_time) as total_number FROM lesson GROUP BY Month(start_time), type_cost ORDER BY Month(start_time);
```
### Show the average number of lesson per month.
``` sql
SELECT AVG(lessons) FROM (SELECT DISTINCT Month(start_time), COUNT(DISTINCT start_time) AS lessons FROM lesson GROUP BY Month(start_time) ORDER BY Month(start_time)) AS l;
```
### Show all instructor how have given the most lessons per lesson type during last month, sorted by number of given lesson.
``` sql
SELECT instructor_id, COUNT(start_time) AS counter FROM (SELECT DISTINCT start_time, instructor_id FROM lesson) AS lesson WHERE start_time LIKE "2020-11%" GROUP BY instructor_id ORDER
BY counter DESC LIMIT 3;
```
## Performed programmatically (Displayed on the web)

### List all ensembles held during the next week, sorted by music genre and weekday.
``` sql
SELECT DISTINCT start_time, day_cost, genre FROM lesson WHERE lesson_type = 2 AND start_time BETWEEN "2020-02-01 00:00:00" AND "2020-02-07 23:59:59";
```
### List the three instrument witht the lowest monthly rental fee. 
``` sql
SELECT DISTINCT instrument, instrument_cost FROM instrument_rental ORDER BY instrument_cost ASC LIMIT 3;
```
### For each instrument tell whenthere it is rented.
``` sql
SELECT * FROM instrument_rental WHERE rental_id IS NOT NULL;
```
### For each instrument tell whenthere it is available for rent.
``` sql
SELECT * FROM instrument_rental WHERE rental_id IS NULL;    
```
### Show the next group lesson for each three instrument.
``` sql
SELECT DISTINCT start_time, instrument, level_cost, total_cost, instructor_id, status FROM lesson WHERE lesson_type = 1 AND instrument IN (SELECT instrument FROM (SELECT DISTINCT instr
ument, instrument_cost FROM instrument_rental ORDER BY instrument_cost ASC LIMIT 3) AS I);
```
