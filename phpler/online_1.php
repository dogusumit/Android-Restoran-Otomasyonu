<?php
include 'sunucu.php';
$q=$vt->query("SELECT adres FROM musteri WHERE telno='".$_REQUEST['kul']."' AND sifre='".$_REQUEST['sfr']."';");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>