<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "job";

// Create connection
$conn = new mysqli($servername,$username,$password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
$res = 2;

if(isset($_POST['contact'])){
    $get = $_POST['contact'];
}
else{
    $get = NULL;
}

if($get!=NULL){
    if($get=="accept"){
        $res=1;
    }
    if($get=="refuse"){
        $res=0; 
    }
    $sql ="UPDATE employeeform SET Decision = '$res' WHERE id='$_POST[id]'";
$query = mysqli_query($conn, $sql);
if(mysqli_query($conn,$sql)){
    header("refresh:1; url=loginemployer.html");
} 
}

?>