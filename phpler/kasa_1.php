<?php
include 'sunucu.php';
$q=$vt->query("SELECT DISTINCT musteri FROM siparis;");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>