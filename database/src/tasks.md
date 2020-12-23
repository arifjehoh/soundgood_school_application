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
CREATE OR REPLACE VIEW rented_instruments AS SELECT rental_due_date AS due_date, instrument, instrument_type AS type FROM instrument_rental WHERE rental_id IS NOT NULL;  
SELECT MONTH(due_date), COUNT(*)/12 AS average FROM rented_instruments GROUP BY due_date;
```

### Show the number of lesson given per month during a specified year.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT MONTH(start_time) AS month FROM lesson GROUP BY start_time;  
SELECT DISTINCT month, COUNT(*) as total_number FROM lessons GROUP BY month ORDER BY month;
```
### Show the total number of lessons.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT MONTH(start_time) AS month FROM lesson GROUP BY start_time;  
SELECT COUNT(DISTINCT start_time) FROM lesson;
```
### Show the total number of lesson per type.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT MONTH(start_time) AS month, type_cost AS type FROM lesson GROUP BY start_time, type_cost;  
SELECT DISTINCT month, type, COUNT(month) AS total_number FROM lessons GROUP BY month, type ORDER BY month;
```
### Show the average number of lesson per month.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT MONTH(start_time) AS month, type_cost AS type FROM lesson GROUP BY start_time, type_cost;  
SELECT AVG(l.tot) AS average FROM (SELECT DISTINCT month, COUNT(*) AS tot FROM lessons GROUP BY month ORDER BY month) AS l;
```
### Show all instructor how have given the most lessons per lesson type during last month, sorted by number of given lesson.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT MONTH(start_time) AS month, type_cost AS type, instructor_id FROM lesson GROUP BY start_time, type_cost, instructor_id;  
SELECT instructor_id, COUNT(month) AS counter FROM lessons WHERE month = 11 GROUP BY instructor_id ORDER BY counter DESC LIMIT 3;
```
## Performed programmatically (Displayed on the web)

### List all ensembles held during the next week, sorted by music genre and weekday.
``` sql
CREATE OR REPLACE VIEW lessons AS SELECT start_time, type_cost AS type, day_cost AS day, genre FROM lesson  WHERE lesson_type = 2 GROUP BY start_time, type_cost, instructor_id, day_cost, genre;
SELECT DISTINCT start_time, day, genre FROM lessons WHERE start_time BETWEEN "2020-02-01" AND "2020-02-07 23:59:59";
```
### List the three instrument witht the lowest monthly rental fee. 
``` sql
CREATE OR REPLACE VIEW rented_instruments AS SELECT DISTINCT instrument, instrument_cost AS cost FROM instrument_rental ORDER BY instrument_cost ASC;  
SELECT * FROM rented_instruments LIMIT 3;
```
### For each instrument tell whenthere it is rented.
``` sql
CREATE OR REPLACE VIEW rented_instruments AS SELECT * FROM instrument_rental WHERE rental_id IS NOT NULL;
SELECT * FROM rented_instruments;
```
### For each instrument tell whenthere it is available for rent.
``` sql
CREATE OR REPLACE VIEW available_instruments AS SELECT * FROM instrument_rental WHERE rental_id IS NULL;
SELECT * FROM available_instruments;
```
### Show the next group lesson for each three instrument.
``` sql
CREATE OR REPLACE VIEW instrument_price AS SELECT DISTINCT instrument, instrument_cost AS cost FROM instrument_rental ORDER BY instrument_cost;
CREATE OR REPLACE VIEW lowest_fee AS SELECT instrument FROM instrument_price ORDER BY cost ASC LIMIT 3;
CREATE OR REPLACE VIEW lessons AS SELECT DISTINCT start_time, instrument, lesson_type, level_cost, total_cost, instructor_id, status FROM lesson;
SELECT DISTINCT start_time, instrument, level_cost, total_cost, instructor_id, status FROM lesson WHERE lesson_type = 1 AND instrument IN (SELECT * FROM lowest_fee);
```
