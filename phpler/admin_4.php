<?php
include 'sunucu.php';
$q=$vt->query("SELECT id,ad,fiyat FROM urunler;");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
$vt->close();
?>