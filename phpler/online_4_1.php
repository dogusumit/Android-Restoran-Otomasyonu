<?php
include 'sunucu.php';
$q=$vt->query("SELECT * FROM musteri WHERE telno='".$_REQUEST['telno']."';");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>