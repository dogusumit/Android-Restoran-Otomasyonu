<?php
include 'sunucu.php';
$q=$vt->query("INSERT INTO siparis (musteri,urun,adet,tutar) VALUES ('".$_REQUEST['musteri']."','".$_REQUEST['urun']."','".$_REQUEST['adet']."','".$_REQUEST['tutar']."');"); 
$q1=$vt->query("INSERT INTO mutfak (musteri,urun,adet,tutar) VALUES ('".$_REQUEST['musteri']."','".$_REQUEST['urun']."','".$_REQUEST['adet']."','".$_REQUEST['tutar']."');"); 
$vt->close();
?>