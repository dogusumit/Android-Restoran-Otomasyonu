<?php
include 'sunucu.php';
$q=$vt->query("SELECT id,urun,adet,tutar FROM siparis WHERE musteri='".$_REQUEST['telno']."';");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>