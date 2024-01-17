-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2024. Jan 17. 22:28
-- Kiszolgáló verziója: 10.4.24-MariaDB
-- PHP verzió: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `familytree`
--
CREATE DATABASE IF NOT EXISTS `familytree` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci;
USE `familytree`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szemelyek`
--

CREATE TABLE IF NOT EXISTS `szemelyek` (
  `szemelyiszam` varchar(11) COLLATE utf8_hungarian_ci NOT NULL,
  `nev` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `szulhely` varchar(35) COLLATE utf8_hungarian_ci NOT NULL,
  `szulido` varchar(10) COLLATE utf8_hungarian_ci NOT NULL,
  `halalhely` varchar(35) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `halalido` varchar(10) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `lany` tinyint(1) NOT NULL,
  PRIMARY KEY (`szemelyiszam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `szemelyek`
--

INSERT INTO `szemelyek` (`szemelyiszam`, `nev`, `szulhely`, `szulido`, `halalhely`, `halalido`, `lany`) VALUES
('00000000000', 'Ismeretlen', 'Ismeretlen', '1800-01-01', 'Ismeretlen', '1800-01-01', 0),
('11952-05-15', 'Mézga Paula', 'Budapest', '1952-05-15', '', '', 1),
('12000-12-25', 'Parlagi Anna', 'Fót', '2000-12-25', '', '', 1),
('12010-04-11', 'Minta Mária', 'Szeged', '2010-04-11', '', '', 1),
('21948-07-19', 'Mézga Géza', 'Budapest', '1948-07-19', 'Fót', '2010-02-11', 0),
('21951-04-13', 'Karacs Ilona', 'Pécs', '1951-04-13', '', '', 1),
('21972-05-28', 'Kele Tünde', 'Győr', '1972-05-28', '', '', 0),
('21974-12-12', 'Gerebély András', 'Győr', '1974-12-12', '', '', 0),
('21977-02-28', 'Mézga Viktor', 'Budapest', '1977-02-28', '', '', 0),
('21988-11-20', 'Minta Géza', 'Mintaváros', '1988-11-20', '', '', 0),
('21990-10-09', 'Kiss Gertrúd', 'Sopon', '1990-10-09', '', '', 0),
('26708307630', 'Cint Ibolya', 'Maglód', '1967-08-30', '', '', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szulok`
--

CREATE TABLE IF NOT EXISTS `szulok` (
  `gyerekId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL,
  `anyaId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL,
  `apaId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`gyerekId`),
  KEY `anyaId` (`anyaId`),
  KEY `apaId` (`apaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `szulok`
--

INSERT INTO `szulok` (`gyerekId`, `anyaId`, `apaId`) VALUES
('11952-05-15', '21990-10-09', '21988-11-20'),
('12010-04-11', '21990-10-09', '21988-11-20'),
('21948-07-19', '', ''),
('21951-04-13', '', ''),
('21972-05-28', '', '21948-07-19'),
('21974-12-12', '', '21948-07-19'),
('21977-02-28', '', '21948-07-19'),
('21988-11-20', '', '21948-07-19');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
