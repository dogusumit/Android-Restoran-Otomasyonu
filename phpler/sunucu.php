<?php
$vt = new mysqli("mysql.hostinger.web.tr","u764426910_user","123456");
if($vt->connect_errno) {
	echo 'Mysqli bağlantı hatası: ' . $vt->connect_errno;
	exit;
}
$vt->select_db("u764426910_db");
?>