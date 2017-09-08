<?php
include 'sunucu.php';
$q=$vt->query("DELETE FROM siparis WHERE musteri='".$_REQUEST['musteri']."';"); 
mysqli_close($vt);
?>