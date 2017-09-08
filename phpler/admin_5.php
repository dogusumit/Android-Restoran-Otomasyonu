<?php
include 'sunucu.php';
$q=$vt->query("DELETE FROM restoran WHERE id='".$_REQUEST['id']."';"); 
mysqli_close($vt);
?>