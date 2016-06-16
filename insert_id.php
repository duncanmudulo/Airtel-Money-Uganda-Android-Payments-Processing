<?php


 include 'connect.php';

 $item_id = $_POST['item_id'];
 $id = $_POST['id'];
 $amount = $_POST['payment_amount'];
 $balance = $_POST['account_balance'];
 $number = $_POST['payment_number'];

  $sql = "insert into payments (item_id, trans_id, amount, balance, number) values ('$item_id', '$id', '$amount', '$balance', '$number')";
  if(mysqli_query($connect,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($connect);



?>
