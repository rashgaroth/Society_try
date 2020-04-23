<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $nama_depan = $_POST['namaDepan'];
    $nama_belakang = $_POST['namaBelakang'];
    $email = $_POST['email_register'];
    $password = $_POST['password_register'];

    $password = password_hash($password, PASSWORD_DEFAULT);

    require_once 'connect.php';

    $sql = "INSERT INTO user (email, nama_depan, nama_belakang, password) VALUES ('$email','$nama_depan', '$nama_belakang', '$password')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>