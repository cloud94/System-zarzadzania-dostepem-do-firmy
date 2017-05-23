-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 23 Maj 2017, 22:44
-- Wersja serwera: 10.1.21-MariaDB
-- Wersja PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `pracownicy`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownicy`
--

CREATE TABLE `pracownicy` (
  `imie` varchar(10) CHARACTER SET latin1 NOT NULL,
  `nazwisko` varchar(20) CHARACTER SET latin1 NOT NULL,
  `nr_pracownika` int(11) NOT NULL,
  `kod_karty` varchar(100) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci ROW_FORMAT=COMPACT;

--
-- Zrzut danych tabeli `pracownicy`
--

INSERT INTO `pracownicy` (`imie`, `nazwisko`, `nr_pracownika`, `kod_karty`) VALUES
('Jan', 'Kowalski', 1, '42412'),
('Adam', 'Malysz', 2, '21wtgdf'),
('Mateusz', 'Kowalski', 3, '21eja2'),
('Adam', 'Psikuta', 4, '124dsa'),
('Jan', 'Muzykant', 5, '42ne32'),
('Adrian', 'Nowak', 6, 'nfd83f'),
('Mariusz', 'Kowal', 7, '32oi53'),
('Stefan', 'B?dzki', 8, 'jo2390'),
('Robert', 'Kubica', 9, '52emw32');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `pracownicy`
--
ALTER TABLE `pracownicy`
  ADD PRIMARY KEY (`nr_pracownika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `pracownicy`
--
ALTER TABLE `pracownicy`
  MODIFY `nr_pracownika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
