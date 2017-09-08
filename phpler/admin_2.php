<?php
include 'sunucu.php';
$q=$vt->query("INSERT INTO urunler (ad,fiyat) VALUES ('".$_REQUEST['ad']."','".$_REQUEST['fiyat']."');"); 
mysqli_close($vt);
?>