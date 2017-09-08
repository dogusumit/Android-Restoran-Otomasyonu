<?php
include 'sunucu.php';
$q=$vt->query("INSERT INTO restoran (user,sifre,yetki) VALUES ('".$_REQUEST['user']."','".$_REQUEST['sifre']."','".$_REQUEST['yetki']."');"); 
$vt->close();
?>