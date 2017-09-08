<?php
include 'sunucu.php';
$q=$vt->query("DELETE FROM siparis WHERE id='".$_REQUEST['id']."';"); 
$q=$vt->query("DELETE FROM mutfak WHERE id='".$_REQUEST['id']."';"); 
mysqli_close($vt);
?>