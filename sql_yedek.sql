
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Anamakine: localhost
-- Üretim Zamanı: 19 Oca 2016, 12:44:02
-- Sunucu sürümü: 10.0.20-MariaDB
-- PHP Sürümü: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Veritabanı: `u764426910_db`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `musteri`
--

CREATE TABLE IF NOT EXISTS `musteri` (
  `telno` varchar(11) COLLATE utf8mb4_turkish_ci NOT NULL,
  `isim` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `sifre` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `adres` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `email` text COLLATE utf8mb4_turkish_ci NOT NULL,
  PRIMARY KEY (`telno`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `musteri`
--

INSERT INTO `musteri` (`telno`, `isim`, `sifre`, `adres`, `email`) VALUES
('5555555555', 'musteri', '123', 'turkey', 'a@asd.com');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `mutfak`
--

CREATE TABLE IF NOT EXISTS `mutfak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `musteri` varchar(11) COLLATE utf8mb4_turkish_ci NOT NULL,
  `urun` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `adet` int(11) NOT NULL,
  `tutar` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `restoran`
--

CREATE TABLE IF NOT EXISTS `restoran` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `sifre` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `yetki` text COLLATE utf8mb4_turkish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci AUTO_INCREMENT=40 ;

--
-- Tablo döküm verisi `restoran`
--

INSERT INTO `restoran` (`id`, `user`, `sifre`, `yetki`) VALUES
(38, 'admin', '123', 'admin'),
(2, 'kasa', '123', 'kasa'),
(3, 'adisyon', '123', 'adisyon'),
(4, 'mutfak', '123', 'mutfak'),
(31, 'garson', '123', 'adisyon');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `siparis`
--

CREATE TABLE IF NOT EXISTS `siparis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `musteri` varchar(11) COLLATE utf8mb4_turkish_ci NOT NULL,
  `urun` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `adet` int(11) NOT NULL,
  `tutar` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `urunler`
--

CREATE TABLE IF NOT EXISTS `urunler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ad` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `fiyat` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci AUTO_INCREMENT=50 ;

--
-- Tablo döküm verisi `urunler`
--

INSERT INTO `urunler` (`id`, `ad`, `fiyat`) VALUES
(22, 'Mevsim Salata', 9),
(21, 'Coban Salatasi', 7),
(19, 'Sezar Salata', 14),
(26, 'Kiymali Pide', 14),
(27, 'Lahmacun', 5),
(28, 'Findik Lahmacun', 3),
(29, 'Icli Kofte', 4.5),
(30, 'Cig Kofte', 13),
(31, 'Ali Nazik', 21),
(32, 'Abagannus', 23),
(33, 'Beyti Kebabi', 20),
(34, 'Patlicanli Kebap', 22),
(35, 'Kuzu Pirzola', 32),
(36, 'Adana Kebap', 18),
(37, 'Kola', 2.5),
(38, 'Fanta', 2.5),
(39, 'Sprite', 2.5),
(40, 'Ayran', 2),
(41, 'Salgam Acili', 1.5),
(42, 'Salgam Acisiz', 1.5),
(45, 'Kakaolu Pasta', 15);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
