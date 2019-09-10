-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 10, 2019 at 06:49 AM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cerberus`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
CREATE TABLE IF NOT EXISTS `attendance` (
  `attendanceID` int(6) NOT NULL AUTO_INCREMENT,
  `PRN` bigint(16) NOT NULL,
  `scheduleID` varchar(6) NOT NULL,
  `timeID` varchar(5) NOT NULL,
  PRIMARY KEY (`attendanceID`),
  KEY `PRN` (`PRN`,`scheduleID`,`timeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `batch`
--

DROP TABLE IF EXISTS `batch`;
CREATE TABLE IF NOT EXISTS `batch` (
  `batchID` varchar(2) NOT NULL,
  `name` varchar(6) NOT NULL,
  PRIMARY KEY (`batchID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
CREATE TABLE IF NOT EXISTS `class` (
  `classID` int(1) NOT NULL,
  `class` varchar(12) NOT NULL,
  PRIMARY KEY (`classID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `datestore`
--

DROP TABLE IF EXISTS `datestore`;
CREATE TABLE IF NOT EXISTS `datestore` (
  `dateID` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`dateID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `day`
--

DROP TABLE IF EXISTS `day`;
CREATE TABLE IF NOT EXISTS `day` (
  `dayID` int(4) NOT NULL AUTO_INCREMENT,
  `dayOfWeek` varchar(9) NOT NULL,
  PRIMARY KEY (`dayID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
CREATE TABLE IF NOT EXISTS `faculty` (
  `facultyID` int(6) NOT NULL,
  `email` varchar(40) NOT NULL,
  `name` varchar(120) NOT NULL,
  `password` varchar(256) NOT NULL,
  PRIMARY KEY (`facultyID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`facultyID`, `email`, `name`, `password`) VALUES
(101, 'ebenezerv99@gmail.com', 'Vraj Kotwala', 'de786632264b9f3a79a7ec15c2460dd079554f0b0ad377cb8408edeb26947eec');

-- --------------------------------------------------------

--
-- Table structure for table `fingerprints`
--

DROP TABLE IF EXISTS `fingerprints`;
CREATE TABLE IF NOT EXISTS `fingerprints` (
  `PRN` bigint(16) NOT NULL,
  `templateID` tinyint(1) NOT NULL,
  `template` blob NOT NULL,
  PRIMARY KEY (`PRN`,`templateID`),
  KEY `PRN` (`PRN`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lab`
--

DROP TABLE IF EXISTS `lab`;
CREATE TABLE IF NOT EXISTS `lab` (
  `labID` varchar(2) NOT NULL,
  `name` varchar(4) NOT NULL,
  PRIMARY KEY (`labID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE IF NOT EXISTS `log` (
  `logID` int(4) NOT NULL AUTO_INCREMENT,
  `logType` varchar(15) NOT NULL,
  `dateID` int(11) NOT NULL,
  `timeID` int(5) NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `log`
--

INSERT INTO `log` (`logID`, `logType`, `dateID`, `timeID`, `comments`) VALUES
(1, 'fingerprints', 0, 0, 'enrolled finger 1 for 2017033800104472');

-- --------------------------------------------------------

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
CREATE TABLE IF NOT EXISTS `otp` (
  `PRN` bigint(16) NOT NULL,
  `OTP` bigint(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `otp`
--

INSERT INTO `otp` (`PRN`, `OTP`) VALUES
(2017033800107501, 223465);

-- --------------------------------------------------------

--
-- Table structure for table `rollcall`
--

DROP TABLE IF EXISTS `rollcall`;
CREATE TABLE IF NOT EXISTS `rollcall` (
  `classID` int(1) NOT NULL,
  `rollNo` varchar(3) NOT NULL,
  `PRN` bigint(16) NOT NULL,
  PRIMARY KEY (`classID`,`rollNo`),
  KEY `PRN` (`PRN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
CREATE TABLE IF NOT EXISTS `slot` (
  `sessionID` varchar(5) NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  PRIMARY KEY (`sessionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `PRN` bigint(16) NOT NULL,
  `name` varchar(120) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(256) NOT NULL,
  PRIMARY KEY (`PRN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`PRN`, `name`, `email`, `password`) VALUES
(2017033800107501, 'Vraj Kotwala', 'iamkotwala@gmail.com', 'de786632264b9f3a79a7ec15c2460dd079554f0b0ad377cb8408edeb26947eec');

-- --------------------------------------------------------

--
-- Table structure for table `studentsubject`
--

DROP TABLE IF EXISTS `studentsubject`;
CREATE TABLE IF NOT EXISTS `studentsubject` (
  `PRN` bigint(16) NOT NULL,
  `subjectID` int(8) NOT NULL,
  `batchID` int(2) NOT NULL,
  PRIMARY KEY (`PRN`,`subjectID`),
  KEY `batchID` (`batchID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `subjectID` varchar(8) NOT NULL,
  `sem` tinyint(1) NOT NULL,
  `subject` varchar(40) NOT NULL,
  `classID` int(1) NOT NULL,
  PRIMARY KEY (`subjectID`,`sem`),
  KEY `classID` (`classID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `timedata`
--

DROP TABLE IF EXISTS `timedata`;
CREATE TABLE IF NOT EXISTS `timedata` (
  `timeID` int(5) NOT NULL AUTO_INCREMENT,
  `time` time NOT NULL,
  PRIMARY KEY (`timeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
CREATE TABLE IF NOT EXISTS `timetable` (
  `scheduleID` varchar(4) NOT NULL,
  `sessionID` varchar(5) NOT NULL,
  `labID` varchar(2) NOT NULL,
  `subjectID` varchar(8) NOT NULL,
  `batchID` varchar(2) NOT NULL,
  `facultyID` varchar(6) NOT NULL,
  `weekID` int(3) NOT NULL,
  `dayID` varchar(4) NOT NULL,
  PRIMARY KEY (`scheduleID`),
  KEY `sessionID` (`sessionID`,`labID`,`subjectID`,`batchID`,`facultyID`,`dayID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `timetable`
--

INSERT INTO `timetable` (`scheduleID`, `sessionID`, `labID`, `subjectID`, `batchID`, `facultyID`, `weekID`, `dayID`) VALUES
('s1', 'sess1', 'L1', 'BCA1538', 'b1', '116', 0, '1'),
('s10', 'sess5', 'L2', 'BCA1001', 'b2', '109', 0, '1'),
('s11', 'sess5', 'L3', 'BCA1001', 'b3', '110', 0, '1'),
('s12', 'sess1', 'L1', 'BCA1538', 'b2', '116', 0, '2'),
('s13', 'sess2', 'L1', 'BCA1539', 'b2', '116', 0, '2'),
('s14', 'sess2', 'L2', 'BCA1530', 'b3', '102', 0, '2'),
('s15', 'sess2', 'L3', 'BCA1501', 'b2', '103', 0, '2'),
('s16', 'sess3', 'L1', 'BCA1403', 'b3', '104', 0, '2'),
('s17', 'sess4', 'L1', 'BCA1010', 'b2', '105', 0, '2'),
('s18', 'sess4', 'L2', 'BCA1307', 'b3', '106', 0, '2'),
('s19', 'sess4', 'L3', 'BCA1208', 'b2', '107', 0, '2'),
('s2', 'sess2', 'L1', 'BCA1301', 'b2', '116', 0, '1'),
('s20', 'sess5', 'L1', 'BCA1105', 'b3', '108', 0, '2'),
('s21', 'sess5', 'L2', 'BCA1106', 'b2', '109', 0, '2'),
('s22', 'sess5', 'L3', 'BCA1001', 'b3', '110', 0, '2'),
('s3', 'sess2', 'L2', 'BCA1308', 'b3', '102', 0, '1'),
('s4', 'sess2', 'L3', 'BCA1303', 'b2', '103', 0, '1'),
('s5', 'sess3', 'L1', 'BCA1539', 'b3', '104', 0, '1'),
('s6', 'sess4', 'L1', 'BCA1105', 'b2', '105', 0, '1'),
('s7', 'sess4', 'L2', 'BCA1106', 'b3', '106', 0, '1'),
('s8', 'sess4', 'L3', 'BCA1208', 'b2', '107', 0, '1'),
('s9', 'sess5', 'L1', 'BCA1001', 'b3', '108', 0, '1');

-- --------------------------------------------------------

--
-- Table structure for table `week`
--

DROP TABLE IF EXISTS `week`;
CREATE TABLE IF NOT EXISTS `week` (
  `weekID` int(3) NOT NULL AUTO_INCREMENT,
  `week` varchar(7) NOT NULL,
  PRIMARY KEY (`weekID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
