-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 08, 2021 at 02:48 AM
-- Server version: 10.2.36-MariaDB-cll-lve
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aips3379_perpus`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id` int(11) NOT NULL,
  `cover` text NOT NULL,
  `content` text NOT NULL,
  `judul` varchar(200) NOT NULL,
  `tahun` int(11) NOT NULL,
  `deskripsi` text NOT NULL,
  `pengarang` varchar(200) NOT NULL,
  `penerbit` varchar(200) NOT NULL,
  `id_kategori` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id`, `cover`, `content`, `judul`, `tahun`, `deskripsi`, `pengarang`, `penerbit`, `id_kategori`) VALUES
(3, 'doctor_PNG15988.png', 'aron-smith-CV-assistant.pdf', 'Buku 1', 2009, 'loren ipsum', 'Anonymous', 'Anonymous', 9),
(4, 'doctor_PNG15988.png', 'aron-smith-CV-assistant.pdf', 'Buku 2', 2009, 'loren ipsum', 'Anonymous', 'Anonymous', 9),
(5, 'doctor_PNG15988.png', 'aron-smith-CV-assistant.pdf', 'Buku 3', 2009, 'loren ipsum', 'Anonymous', 'Anonymous', 9),
(6, 'doctor_PNG15988.png', 'aron-smith-CV-assistant.pdf', 'Buku 4', 2009, 'loren ipsum', 'Anonymous', 'Anonymous', 9),
(7, 'doctor_PNG15988.png', 'aron-smith-CV-assistant.pdf', 'Buku 5', 2009, 'loren ipsum', 'Anonymous', 'Anonymous', 9),
(8, 'arsitektur.PNG', 'pengenalan-visual-basic-6-0.pdf', 'Arsitektur Komputer', 0, '\r\n                                                        ', 'Rolly Maulana Awangga', '-', 11);

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id`, `nama_kategori`, `deskripsi`) VALUES
(5, 'Film', '                                                            \r\n                                                        '),
(6, 'Novel', '                                                            \r\n                                                        '),
(7, 'Other', '                                                            \r\n                                                        '),
(8, 'Fantasy', '                                                            \r\n                                                        '),
(9, 'Action', '                                                            \r\n                                                        '),
(10, 'Anime', '                                                            \r\n                                                        '),
(11, 'Informatika', 'Buku Informatika dan Komputer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
