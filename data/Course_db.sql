-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2020 at 01:36 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `course_db`
--
DROP DATABASE IF EXISTS `course_db`;
CREATE DATABASE IF NOT EXISTS `course_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `course_db`;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--
-- Creation: Jun 01, 2020 at 12:27 AM
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `IntAttribute` mediumint(9) NOT NULL AUTO_INCREMENT,
  `StringAttribute` varchar(15) NOT NULL,
  `DoubleAttribute` decimal(4,2) NOT NULL,
  `LocalDateAttribute` date NOT NULL,
  PRIMARY KEY (`IntAttribute`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `course`:
--

--
-- Dumping data for table `course`
--

REPLACE DELAYED INTO `course` (`IntAttribute`, `StringAttribute`, `DoubleAttribute`, `LocalDateAttribute`) VALUES
(80, 'murad', '4.50', '1990-03-04'),
(88, ' joker', '19.00', '2014-01-15'),
(89, ' ali', '9.80', '2000-05-01'),
(94, 'wael', '18.00', '1997-04-18'),
(101, 'sdasd', '7.80', '1997-07-23');


--
-- Metadata
--
USE `phpmyadmin`;

--
-- Metadata for table course
--

--
-- Metadata for database course_db
--
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
