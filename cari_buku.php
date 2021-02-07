<?php

	require_once '../include/koneksi.php';
    
    $judul = $_GET['judul'];
	$stmt =mysqli_query($koneksi,"SELECT * FROM buku WHERE judul LIKE '%".$judul."%'");

	$res = array();
	$i = 0;
	while($d = mysqli_fetch_array($stmt)){
		$res[$i]['id']=$d['id'];
		$res[$i]['cover']="https://perpus.ai-pktj.com/images/".$d['cover'];
        $res[$i]['content']="https://perpus.ai-pktj.com/files/".$d['content'];
        $res[$i]['judul']=$d['judul'];
        $res[$i]['tahun']=$d['tahun'];
        $res[$i]['deskripsi']=$d['deskripsi'];
        $res[$i]['pengarang']=$d['pengarang'];
        $res[$i]['penerbit']=$d['penerbit'];
        $res[$i]['id_kategori']=$d['id_kategori'];
		$i++;
	}

	echo json_encode(array('success'=>1,'result'=>$res));

?>