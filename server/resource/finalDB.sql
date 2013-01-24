--
-- MySQL 5.5.24
-- Thu, 24 Jan 2013 11:10:07 +0000
--

CREATE DATABASE `asa` DEFAULT CHARSET latin1;

USE `asa`;

CREATE TABLE `device` (
   `id` int(10) unsigned not null auto_increment,
   `name` varchar(150) CHARSET latin1 not null,
   `location` varchar(150) not null,
   `background_url` varchar(150) not null,
   `logo_url` varchar(255) not null,
   `device_url` varchar(150) not null,
   `watt_total` float not null,
   `devide_by` int(10) unsigned not null,
   `sensor` text CHARSET latin1 not null,
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=301;

INSERT INTO `device` (`id`, `name`, `location`, `background_url`, `logo_url`, `device_url`, `watt_total`, `devide_by`, `sensor`) VALUES 
('7', 'Koffieset apparaat', 'Kiosk', 'background_koffie.png', 'icon_koffie.png', '', '3000', '1', '?'),
('2', 'Printer', 'hok 3.50', 'background_printer.png', 'icon_printer.png', '', '1530', '11', '?'),
('3', 'Cola automaat', 'Kantine', 'background_colaautomaat.png', 'icon_colaautomaat.png', '', '780', '1', '?'),
('8', 'Beamer', 'Social Wall', 'background_beamer.png', 'icon_beamer.png', '', '1237.4', '6', '?'),
('5', 'Koelschap', 'Kantine', 'background_koeling.png', 'icon_keuken.png', '', '2500', '1', '?'),
('6', 'TL buis', 'Door het hele gebouw', 'background_tlbuis.png', 'icon_tlbuis.png', '', '15', '1', '?'),
('4', 'Spaarlamp', 'Door het hele gebouw', 'background_spaarlamp.png', 'icon_spaarlamp.png', '', '10', '1', ''),
('1', 'TV', 'Ingang', 'background_tv.png', 'icon_tv.png', '', '56', '1', ''),
('9', 'Elektrische scooter', 'Energietechnieklab', 'background_scooter.png', 'icon_scooter.png', '', '0.395', '1', '');

CREATE TABLE `highscore` (
   `id` int(10) unsigned not null auto_increment,
   `score` float not null,
   `photo_url` varchar(255) not null,
   `timestamp` timestamp not null default CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0;