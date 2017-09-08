<?php
include 'sunucu.php';
$q=$vt->query("SELECT id,musteri,urun,adet FROM mutfak;");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>