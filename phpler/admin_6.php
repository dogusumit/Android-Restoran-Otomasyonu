<?php
include 'sunucu.php';
$q=$vt->query("DELETE FROM urunler WHERE id='".$_REQUEST['id']."';"); 
mysqli_close($vt);
?>