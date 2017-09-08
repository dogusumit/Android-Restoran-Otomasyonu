<?php
include 'sunucu.php';
$q=$vt->query("SELECT urun,adet,tutar FROM siparis WHERE musteri='".$_REQUEST['musteri']."';");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>