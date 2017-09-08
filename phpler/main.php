<?php
include 'sunucu.php';
$q=$vt->query("SELECT yetki FROM restoran WHERE user='".$_REQUEST['kul']."' AND sifre='".$_REQUEST['sfr']."';");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
mysqli_close($vt);
?>