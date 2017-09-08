<?php
include 'sunucu.php';

$q=$vt->query("DELETE FROM musteri WHERE telno='".$_REQUEST['telno']."';");
$q=$vt->query("INSERT INTO musteri(telno,isim,sifre,adres,email) VALUES ('".$_REQUEST['telno']."','".$_REQUEST['isim']."','".$_REQUEST['sifre']."','".$_REQUEST['adres']."','".$_REQUEST['email']."');"); 

mysqli_close($vt);
?>