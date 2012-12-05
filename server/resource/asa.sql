-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 05, 2012 at 10:17 AM
-- Server version: 5.5.28
-- PHP Version: 5.4.6-1ubuntu1.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `asa`
--

-- --------------------------------------------------------

--
-- Table structure for table `device`
--

CREATE TABLE IF NOT EXISTS `device` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET latin1 NOT NULL,
  `photo_url` varchar(255) CHARACTER SET latin1 NOT NULL,
  `logo_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `watt_total` float NOT NULL,
  `devide_by` int(10) unsigned NOT NULL,
  `sensor` text CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `device`
--

INSERT INTO `device` (`id`, `name`, `photo_url`, `logo_url`, `watt_total`, `devide_by`, `sensor`) VALUES
(1, 'Koffieset apparaat', 'background_koffie.png', 'icon_koffie.png', 150, 1, '?'),
(2, 'Printer', 'background_printer.png', 'icon_printer.png', 52, 1, '?'),
(3, 'Cola automaat', 'background_cola.png', 'icon_automaat.png', 541, 3, '?'),
(4, 'Beamer', 'background_beamert.png', 'icon_beamer.png', 458, 1, '?'),
(5, 'Kantiene', 'background_koeling.png', 'icon_keuken.png', 4032, 1, '?'),
(6, 'TV/Lamp', 'background_tv.png', 'icon_lamp.png', 153, 8, '?');

-- --------------------------------------------------------

--
-- Table structure for table `highscore`
--

CREATE TABLE IF NOT EXISTS `highscore` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `score` float NOT NULL,
  `photo_url` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=42 ;

--
-- Dumping data for table `highscore`
--

INSERT INTO `highscore` (`id`, `score`, `photo_url`, `timestamp`) VALUES
(25, 5462.16, 'fewffs', '2012-12-03 11:05:09'),
(26, 5.16, 'fewffs', '2012-12-03 11:05:22'),
(27, 5.16, 'fewffs', '2012-12-03 11:13:14'),
(28, 5.16, 'fewffs', '2012-12-03 11:13:56'),
(29, 5.16, 'fewffs', '2012-12-03 11:37:29'),
(30, 5.16, 'fewffs', '2012-12-03 11:40:18'),
(31, 5.16, 'fewffs', '2012-12-03 11:41:24'),
(32, 5.16, 'fewffs', '2012-12-03 11:42:02'),
(33, 5.16, 'fewffs', '2012-12-03 11:44:34'),
(34, 5.16, 'fewffs', '2012-12-03 11:44:57'),
(35, 5.16, 'fewffs', '2012-12-03 11:45:09'),
(36, 5.16, 'fewffs', '2012-12-03 11:45:57'),
(37, 5.16, 'fewffs', '2012-12-03 11:46:27'),
(38, 0.0158697, 'fotourl', '2012-12-04 09:31:37'),
(39, 0.845042, 'fotourl', '2012-12-04 09:31:51'),
(40, 5.16, 'fewffs', '2012-12-04 09:32:32'),
(41, 0.0599226, 'fotourl', '2012-12-04 09:32:42');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
