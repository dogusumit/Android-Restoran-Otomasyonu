<?php
include 'sunucu.php';
$q=$vt->query("SELECT isim FROM musteri WHERE telno='".$_REQUEST['telno']."';");
if($q->num_rows>0)
{
while($e=$q->fetch_assoc())
        $output[]=$e;
print(json_encode($output));
}
else
{
$q=$vt->query("INSERT INTO musteri(telno,isim,sifre,adres,email) VALUES ('".$_REQUEST['telno']."','".$_REQUEST['isim']."','".$_REQUEST['sifre']."','".$_REQUEST['adres']."','".$_REQUEST['email']."');"); 
print('[{"isim":"OK"}]');
}
mysqli_close($vt);
?>



