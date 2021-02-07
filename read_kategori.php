<?php

	require_once '../include/koneksi.php';
	$stmt =mysqli_query($koneksi,"SELECT * FROM kategori");
	$res = array();
	$i = 0;
	while($d = mysqli_fetch_array($stmt)){
		$res[$i]['id']=$d['id'];
        $res[$i]['nama_kategori']=$d['nama_kategori'];
        $res[$i]['deskripsi']=$d['deskripsi'];
		$i++;
	}
	echo json_encode(array('success'=>1,'result'=>$res));

?>