<?php
include 'sunucu.php';
$q=$vt->query("DELETE FROM mutfak WHERE id='".$_REQUEST['id']."';"); 
mysqli_close($vt);
?>