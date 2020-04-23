<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $nama_depan = $_POST['nama_depan'];
    $email = $_POST['email'];
    $id = $_POST['id'];

    require_once 'connect.php';

    $sql = "UPDATE user SET name='$nama_depan', email='$email' WHERE id='$id' ";

    if(mysqli_query($conn, $sql)) {

          $result["success"] = "1";
          $result["message"] = "success";

          echo json_encode($result);
          mysqli_close($conn);
      }
  }

else{

   $result["success"] = "0";
   $result["message"] = "error!";
   echo json_encode($result);

   mysqli_close($conn);
}

?>


