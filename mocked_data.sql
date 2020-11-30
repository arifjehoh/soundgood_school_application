-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: school
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `student_id` int NOT NULL,
  `save_application` bit(1) NOT NULL,
  `contactable` bit(1) NOT NULL,
  `created_at` timestamp(6) NOT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  `skill_id` int DEFAULT NULL,
  `lesson_id` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  KEY `FK_application_2` (`skill_id`),
  KEY `FK_application_4` (`lesson_id`),
  CONSTRAINT `FK_application_0` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
  CONSTRAINT `FK_application_1` FOREIGN KEY (`student_id`) REFERENCES `invoice` (`student_id`),
  CONSTRAINT `FK_application_2` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`skill_id`),
  CONSTRAINT `FK_application_3` FOREIGN KEY (`student_id`) REFERENCES `skill` (`student_id`),
  CONSTRAINT `FK_application_4` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`),
  CONSTRAINT `FK_application_6` CHECK ((`age` >= 16))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_detail`
--

DROP TABLE IF EXISTS `contact_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_detail` (
  `student_id` int NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `home_number` varchar(10) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `FK_contact_detail_0` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_detail`
--

LOCK TABLES `contact_detail` WRITE;
/*!40000 ALTER TABLE `contact_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ensemble_skill`
--

DROP TABLE IF EXISTS `ensemble_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ensemble_skill` (
  `instructor_id` int NOT NULL,
  `ensemble_genre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`instructor_id`),
  CONSTRAINT `FK_ensemble_skill_0` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ensemble_skill`
--

LOCK TABLES `ensemble_skill` WRITE;
/*!40000 ALTER TABLE `ensemble_skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `ensemble_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `instructor_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `social_security_number` varchar(12) NOT NULL,
  `age` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(10) NOT NULL,
  `home_number` varchar(10) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`instructor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (1,'arif','jehda-oh','199707163863',23,'stockholm','12345','bergengatan 3','072123456',NULL,'arif.jehda-oh@mail.com'),(2,'mattias','svensson','198009038751',40,'stockholm','12345','Waterside','072123457',NULL,'mattias.svensson@mail.com'),(3,'maria','qvist','199407129228',26,'stockholm','12345','Ash Lane','072123458',NULL,'maria.qvist@mail.com'),(4,'johan','lund','197903109469',41,'stockholm','12345','Dale Close','072123459',NULL,'johan.lund@mail.com'),(5,'ahmed','abdullah','198804295819',32,'stockholm','12345','Hawthorn Avenue','072123460',NULL,'ahmed.abdullah@mail.com'),(6,'tiger','wood','197502213365',45,'stockholm','12345','South Street','072123461',NULL,'tiger.wood@mail.com'),(7,'lisa','blackpink','198302204866',37,'stockholm','12345','Hastings Road','072123462',NULL,'lisa.blackpink@mail.com'),(8,'park','chul','199202233739',28,'stockholm','12345','Bath Road','072123463',NULL,'park.chul@mail.com'),(9,'christian','josseffson','199111185215',28,'stockholm','12345','Priory Close','072123464',NULL,'christian.josseffson@mail.com'),(10,'dewey','svensson','199004067193',30,'stockholm','12345','Windsor Street','072123465',NULL,'dewey.svensson@mail.com'),(11,'Taylor','lund','198203033875',38,'stockholm','12345','Sandringham Close','072123466',NULL,'Taylor.lund@mail.com'),(12,'martin','Ek','197906272873',41,'stockholm','12345','Oakwood Close','072123467',NULL,'martin.Ek@mail.com'),(13,'zara','larsson','198105176300',39,'stockholm','12345','Almond Close','072123468',NULL,'zara.larsson@mail.com'),(14,'sara','varmlund','199008258150',30,'stockholm','12345','Beacon Road','072123469',NULL,'sara.varmlund@mail.com'),(15,'jeff','dum','197604076891',44,'stockholm','12345','Hillcrest','072123470',NULL,'jeff.dum@mail.com'),(16,'ola','karlsson','197604133771',44,'stockholm','12345','Windsor Avenue','072123471',NULL,'ola.karlsson@mail.com'),(17,'tim','berg','199209195967',28,'stockholm','12345','The Grove','072123472',NULL,'tim.berg@mail.com'),(18,'tove','lund','198507251706',35,'stockholm','12345','Primrose Lane','072123473',NULL,'tove.lund@mail.com'),(19,'kanye','west','198004229745',40,'stockholm','12345','Windermere Avenue','072123474',NULL,'kanye.west@mail.com'),(20,'maria','carey','199301032466',27,'stockholm','12345','Warwick Avenue','072123475',NULL,'maria.carey@mail.com');
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_payment`
--

DROP TABLE IF EXISTS `instructor_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_payment` (
  `instructor_id` int DEFAULT NULL,
  `due_date` date NOT NULL,
  `total_income` double(6,2) NOT NULL,
  `created_at` timestamp(6) NOT NULL,
  KEY `FK_instructor_payment_0` (`instructor_id`),
  CONSTRAINT `FK_instructor_payment_0` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_payment`
--

LOCK TABLES `instructor_payment` WRITE;
/*!40000 ALTER TABLE `instructor_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `instructor_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrument_rental`
--

DROP TABLE IF EXISTS `instrument_rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instrument_rental` (
  `rental_id` int DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `instrument` varchar(255) DEFAULT NULL,
  `instrument_type` varchar(255) DEFAULT NULL,
  `instrument_cost` double(6,2) DEFAULT NULL,
  KEY `FK_instrument_rental_0` (`rental_id`),
  KEY `FK_instrument_rental_1` (`student_id`),
  CONSTRAINT `FK_instrument_rental_0` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`rental_id`),
  CONSTRAINT `FK_instrument_rental_1` FOREIGN KEY (`student_id`) REFERENCES `rental` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrument_rental`
--

LOCK TABLES `instrument_rental` WRITE;
/*!40000 ALTER TABLE `instrument_rental` DISABLE KEYS */;
INSERT INTO `instrument_rental` VALUES (NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'guitar','string',50.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'viola','string',69.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'harp','string',99.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'drums','percussion',45.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'trombone','wind',75.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'piano','keyboard',50.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'xylophone','percussion',45.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00),(NULL,NULL,'keyboard','keyboard',75.00);
/*!40000 ALTER TABLE `instrument_rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `student_id` int NOT NULL,
  `total_cost` varchar(255) NOT NULL,
  `due_date` date NOT NULL,
  `discount` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) NOT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `FK_invoice_0` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `lesson_id` int NOT NULL AUTO_INCREMENT,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `instrument` varchar(255) NOT NULL,
  `lesson_type` decimal(3,0) NOT NULL,
  `type_cost` varchar(255) DEFAULT NULL,
  `day_cost` varchar(255) DEFAULT NULL,
  `level_cost` varchar(255) DEFAULT NULL,
  `total_cost` double(6,2) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`lesson_id`),
  KEY `FK_lesson_0` (`instructor_id`),
  KEY `FK_lesson_2` (`type_cost`),
  KEY `FK_lesson_3` (`day_cost`),
  KEY `FK_lesson_4` (`level_cost`),
  CONSTRAINT `FK_lesson_0` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`),
  CONSTRAINT `FK_lesson_2` FOREIGN KEY (`type_cost`) REFERENCES `price` (`price_name`),
  CONSTRAINT `FK_lesson_3` FOREIGN KEY (`day_cost`) REFERENCES `price` (`price_name`),
  CONSTRAINT `FK_lesson_4` FOREIGN KEY (`level_cost`) REFERENCES `price` (`price_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parent` (
  `student_id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `FK_parent_0` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price` (
  `price_name` varchar(255) NOT NULL,
  `price` double(6,2) DEFAULT NULL,
  PRIMARY KEY (`price_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES ('advanced',50.00),('beginner',20.00),('ensemble',45.00),('friday',0.00),('group',75.00),('individual',50.00),('intermediate',30.00),('monday',0.00),('saturday',50.00),('sunday',100.00),('thursday',0.00),('tuesday',0.00),('wednesday',0.00);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental` (
  `rental_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `rental_due_date` timestamp(6) NULL DEFAULT NULL,
  `total_cost` double(6,2) DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `FK_rental_0` (`student_id`),
  CONSTRAINT `FK_rental_0` FOREIGN KEY (`student_id`) REFERENCES `invoice` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `skill_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `instrument` varchar(255) NOT NULL,
  `instrument_level` decimal(3,0) NOT NULL,
  `passed_audition` bit(1) DEFAULT NULL,
  `created_at` timestamp(6) NOT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`skill_id`),
  KEY `FK_skill_0` (`student_id`),
  CONSTRAINT `FK_skill_0` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `social_security_number` varchar(12) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Moa','Cederblom',30,'stockholm','12345','Windermere Avenue','199005177924'),(2,'Eljena','Stenbeck',25,'stockholm','12345','Burnside','199506065704'),(3,'Samuel','Hammarberg',30,'stockholm','12345','Woodlands Road','199006084830'),(4,'Casper','Holmquist',28,'stockholm','12345','Jubilee Road','199201038476'),(5,'Lizette','Sparv',23,'stockholm','12345','Brighton Road','199731114467'),(6,'Ã–nne','EdstrÃ¶m',26,'stockholm','12345','Severn Road','199405149177'),(7,'Birgit','Hagelin',23,'stockholm','12345','Farm Lane','199703229102'),(8,'Vera','Sparre',20,'stockholm','12345','Hamilton Street','200010318302'),(9,'Simone','Bruun',24,'stockholm','12345','Valley View','199608132032'),(10,'Oscar','Sohlmann',19,'stockholm','12345','Stratford Road','200110259474'),(11,'Josefina','Rehn',18,'stockholm','12345','Albert Street','200203137908'),(12,'BÃ¶rje','WikstrÃ¶m',27,'stockholm','12345','Abbey Street','199309203972'),(13,'Emilia','Lagerkvist',26,'stockholm','12345','Rectory Lane','199404294525'),(14,'Filip','Holmlund',24,'stockholm','12345','The Pines','199605296850'),(15,'Alex','Akerman',21,'stockholm','12345','King Edward Road','199811015712'),(16,'My','Tornquist',17,'stockholm','12345','Nursery Gardens','200303147627'),(17,'Josefine','MalmstrÃ¶m',25,'stockholm','12345','The Paddocks','199509234509'),(18,'Emma','JÃ¶nsson',29,'stockholm','12345','Salisbury Road','199105195647'),(19,'Minna','Pettersson',28,'stockholm','12345','Dorset Road','199111117902'),(20,'Malin','Lind',22,'stockholm','12345','East View','199808071901'),(21,'Sebastian','Ohlin',24,'stockholm','12345','Maple Close','199606166835'),(22,'Cecilia','Bjorkman',17,'stockholm','12345','Mount Pleasant','200309114228'),(23,'GÃ¶sta','Anderson',22,'stockholm','12345','Willow Drive','199801146876'),(24,'Marcus','Richardsson',22,'stockholm','12345','Hazel Road','199802155753'),(25,'Stina','Ekman',29,'stockholm','12345','Keats Close','199101319920'),(26,'Linus','Odenberg',24,'stockholm','12345','Newton Close','199631114152'),(27,'Josef','Dalin',28,'stockholm','12345','Springfield Avenue','199210153873'),(28,'Gabriel','Holm',21,'stockholm','12345','Cromwell Road','199905193147'),(29,'Holvaster','Hellberg',22,'stockholm','12345','Osborne Road','199810069250'),(30,'Anne','LÃ¶fgren',26,'stockholm','12345','Cecil Street','199401247023'),(31,'Ottilia','LiljestrÃ¶m',21,'stockholm','12345','Barton Road','199906203303'),(32,'Mirabella','Sandberg',30,'stockholm','12345','Coach Road','199001218948'),(33,'Samuel','Henriksson',21,'stockholm','12345','Woodland Avenue','199904282057'),(34,'Sanna','Hammarberg',28,'stockholm','12345','Church Place','199205021380'),(35,'Pia','Lundquist',20,'stockholm','12345','Winchester Road','200005152420'),(36,'Melina','Skoglund',19,'stockholm','12345','Partridge Close','200110204947'),(37,'Anja','Ohlson',21,'stockholm','12345','Vicarage Gardens','199904096462'),(38,'Nichole','EkstrÃ¶m',29,'stockholm','12345','Durham Road','199131278427'),(39,'Tilde','Forslund',16,'stockholm','12345','Melrose Avenue','200402067080'),(40,'Yvonne','StrÃ¶m',29,'stockholm','12345','Abbey Close','199110024806'),(41,'Rebecca','Magnusson',19,'stockholm','12345','Eastgate','200104236165'),(42,'Katja','Friberg',19,'stockholm','12345','Manor Drive','200106177940'),(43,'Adrian','Bergquist',22,'stockholm','12345','Station Street','199801136199'),(44,'Jennifer','SjÃ¶holm',26,'stockholm','12345','Hazel Avenue','199404179805'),(45,'Madelene','Nordlund',30,'stockholm','12345','South View','199009023964'),(46,'Hildr','Blomgren',19,'stockholm','12345','Riverside Road','200107226660'),(47,'Ã–rjan','Larsson',23,'stockholm','12345','Ivy Close','199611021174'),(48,'Krister','NystrÃ¶m',23,'stockholm','12345','Howard Close','199731291497'),(49,'Josefine','Wahlgren',23,'stockholm','12345','Brookside Close','199702069540'),(50,'Michael','Jonsson',22,'stockholm','12345','Heron Close','199801093691');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor_skill`
--

DROP TABLE IF EXISTS `tutor_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor_skill` (
  `instructor_id` int DEFAULT NULL,
  `instrument` varchar(255) NOT NULL,
  `instrument_level` decimal(3,0) DEFAULT NULL,
  KEY `FK_tutor_skill_0` (`instructor_id`),
  CONSTRAINT `FK_tutor_skill_0` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_skill`
--

LOCK TABLES `tutor_skill` WRITE;
/*!40000 ALTER TABLE `tutor_skill` DISABLE KEYS */;
INSERT INTO `tutor_skill` VALUES (1,'guitar',1),(1,'harp',2),(1,'piano',0),(1,'piano',2),(1,'xylophone',1),(1,'keyboard',1),(2,'guitar',1),(2,'harp',1),(2,'harp',0),(2,'drums',1),(2,'drums',2),(2,'piano',1),(2,'keyboard',1),(3,'viola',0),(3,'drums',2),(3,'xylophone',0),(3,'keyboard',0),(4,'guitar',1),(4,'guitar',2),(4,'harp',2),(4,'drums',1),(4,'trombone',0),(4,'piano',0),(4,'piano',1),(4,'xylophone',2),(4,'keyboard',0),(5,'guitar',1),(5,'viola',0),(5,'harp',2),(5,'harp',1),(5,'harp',0),(5,'drums',1),(5,'drums',2),(5,'trombone',1),(5,'piano',0),(5,'piano',1),(6,'viola',2),(6,'harp',1),(6,'drums',1),(6,'trombone',0),(6,'trombone',1),(6,'piano',0),(6,'xylophone',1),(6,'xylophone',0),(7,'guitar',0),(7,'guitar',2),(7,'viola',0),(7,'harp',1),(7,'drums',1),(7,'trombone',2),(7,'piano',0),(7,'piano',2),(7,'xylophone',2),(7,'xylophone',0),(7,'keyboard',0),(8,'guitar',0),(8,'viola',2),(8,'viola',0),(8,'drums',0),(8,'trombone',0),(8,'trombone',1),(8,'piano',2),(8,'piano',0),(8,'xylophone',0),(8,'xylophone',2),(8,'keyboard',1),(9,'guitar',0),(9,'guitar',1),(9,'guitar',2),(9,'viola',0),(9,'viola',1),(9,'harp',1),(9,'drums',1),(9,'trombone',2),(9,'piano',1),(9,'piano',0),(9,'xylophone',1),(9,'keyboard',1),(10,'guitar',2),(10,'viola',0),(10,'harp',2),(10,'drums',0),(10,'trombone',1),(10,'piano',0),(10,'piano',2),(10,'xylophone',2),(10,'keyboard',0),(10,'harp',1),(11,'guitar',0),(11,'keyboard',0),(11,'viola',1),(11,'trombone',2),(11,'piano',0),(11,'xylophone',2),(11,'viola',0),(11,'trombone',1),(11,'piano',2),(11,'xylophone',0),(12,'guitar',0),(12,'piano',2),(12,'xylophone',0),(12,'keyboard',0),(13,'guitar',2),(13,'harp',1),(13,'drums',1),(13,'piano',0),(13,'xylophone',2),(13,'keyboard',0),(13,'viola',1),(13,'trombone',0),(13,'viola',0),(13,'trombone',1),(13,'trombone',2),(14,'guitar',1),(14,'drums',0),(14,'piano',1),(15,'guitar',2),(15,'trombone',0),(15,'xylophone',0),(15,'keyboard',0),(15,'viola',2),(15,'drums',1),(15,'piano',0),(15,'viola',1),(15,'drums',2),(15,'piano',1),(16,'keyboard',2),(16,'guitar',2),(16,'harp',0),(16,'drums',0),(16,'trombone',0),(16,'piano',2),(16,'xylophone',0),(16,'keyboard',1),(16,'guitar',1),(16,'harp',2),(16,'drums',2),(16,'trombone',2),(16,'keyboard',0),(16,'guitar',0),(17,'drums',0),(17,'trombone',2),(17,'piano',1),(17,'xylophone',0),(18,'harp',0),(18,'trombone',2),(18,'drums',2),(18,'xylophone',1),(18,'drums',1),(18,'xylophone',0),(18,'drums',0),(19,'piano',2),(19,'piano',0),(19,'keyboard',1);
/*!40000 ALTER TABLE `tutor_skill` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-30 15:30:19
