<?php
//This is an example file to run when requesting the user to confirm his transaction id
//You can create an activity to verify payments in which you have an input field for the transaction_id
//Here, i used a WebView in an activity
session_start(); //we are starting the payment confirmation session

error_reporting(0);

include("connect.php");

<html>

</head>
	<body>

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

			$blacklist = "update payments set used='1' where trans_id='$id'"; //simply blaclist any used transaction id's


			$result = mysqli_query($connect, $sql);

			$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

			extract($row);

			if($row){
				//extra processing for you
				
				
			}	
			
		</body>
	</html>

