<?php
include 'sunucu.php';
$q=$vt->query("SELECT id,user,yetki FROM restoran;");
while($e=$q->fetch_assoc())
        $output[]=$e;
 
print(json_encode($output));
 
$vt->close();
?>