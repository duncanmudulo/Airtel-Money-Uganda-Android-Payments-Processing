<?php
session_start(); //we are starting the payment session

error_reporting(0);

include("connect.php");


	if(isset($_SESSION['app_price'])&&isset($_SESSION['app_apk'])&&isset($_SESSION['application_id'])){

						$price = $_SESSION['app_price'];

						$apk = $_SESSION['app_apk'];

						$application_id = $_SESSION['application_id'];

					//	echo "The app costs $price";
					//	echo "The app is located at $apk";
 
				}
else{
			$application_id = $_REQUEST['app_id'];

				//echo "The application_id is $application_id";

				$app_sql = "select * from apps where app_id = '$application_id'";

				$data = mysqli_query($connect, $app_sql);

				$data_row = mysqli_fetch_array($data, MYSQLI_ASSOC);

				extract($data_row);

				//echo "The price is $price";



				

					$_SESSION['app_price'] = $price;

						$_SESSION['app_apk'] = $apk;

						 	$_SESSION['application_id'] = $application_id;

					//	echo "The app costs $_SESSION[app_price]";
					//	echo "The app is located at $_SESSION[app_apk]";

		}	




				
?>
<!DOCTYPE html>
<html>
<head>
	<title>Enter Trans ID</title>
	<link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>


	<script type="text/javascript">
	function wrongTransactionID(){
		app.wrongTransactionId();
			
	}

	function wrongNumber(){
		app.wrongNumber();
	}

	function usedTransactionID(){
		app.usedTransactionID();
	}

	function underPayment(){
		  app.underPayment();
	}

	function overPayment(){
		app.overPayment();
				 }

	function okPayment(){
		app.okPayment();
					}
	function hideLink(){
		$('#dlink').hide();
	}

	</script>

</head>
		<body>

			<script type="text/javascript" src="js/jquery.js"></script>
			<script type="text/javascript" src="js/materialize.min.js"></script>

			

			

			<div class="row">
				<div class="container">
				<form class="col s9" method="post" action="<?php echo ($_SERVER['PHP_SELF']);  ?>">
					<div class="row">
					<div class="text-field col s12">
					<br>
					<br>
							<h5 style="width: 100%">Enter Transaction ID</h5>
						</div>

						<div class="input-field col s12">
							<input id="trans_id" type="number" name="trans_id">
								<label for="trans_id">Transaction ID</label>
						</div>

						

						<div class="col s12" type="submit">
							
						<button class="btn waves-effect waves-light" type="submit" name="action">
							CHECK
						</button>
						</div>
					</div>
				</form>


				<div class="col s9" style="font-size: 16px;">

			

			<?php
			

			$id = $_POST['trans_id'];

			if(isset($id)){
			//echo "The transaction id is $id";

			$sql = "select * from payments where trans_id = '$id'";

			$blacklist = "update payments set used='1' where trans_id='$id'";


			$result = mysqli_query($connect, $sql);

			$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

			extract($row);

			if($row){


				//echo "The app costs $app_price";
				//echo "The app is located at $app_apk";	


			if($number!='0758455142'){

				echo "<script type='text/javascript'>wrongNumber();</script>";

			 		$_SESSION = array();

			 		session_destroy();

			}


			else if($used == 1){

			 		echo "<script type='text/javascript'>usedTransactionID();</script>";

			 		$_SESSION = array();

			 		session_destroy();

			 }


			 else if($amount < $price){

					echo "<script type='text/javascript'>underPayment();</script>";

					mysqli_query($connect, $blacklist);

					$_SESSION = array();

					session_destroy();

					
			 
			 }

			 else if($amount > $price){
			 		echo "<script type='text/javascript'>overPayment();</script>";

			 		mysqli_query($connect, $blacklist);

			 		$_SESSION = array();

			 		session_destroy();
			 	

			 }

			 else if($amount == $price){

			 	echo "<script type='text/javascript'>okPayment();</script>";

			 	mysqli_query($connect, $blacklist);
			
			    echo "<h5 id='dlink'>Click <a href='$apk' onclick='hideLink();'>Here</a> to download the app</h5>";

			   // echo "<script type='text/javascript'>wrongTransactionID();</script>";


			    $_SESSION = array();

			    session_destroy();
			 }

			 
			}
			else{

			echo "<script type='text/javascript'>wrongTransactionID();</script>";


			}
		}

			?>

			

			</div>
				</div>

			</div>

			

			
			
		</body>
	</html>

