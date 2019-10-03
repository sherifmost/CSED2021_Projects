<?php

/*include("homepage.html");
if(isset($_POST['a'])){
    include("ApplyForm.html");*/
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

    $name = $_POST['CompanyName'];
    $address = $_POST['address']; 
    $field = $_POST['Field'];
    $position = $_POST['Position'];
    $emptypositions = $_POST['emptyPositions'];
    $qualify = $_POST['qualification'];
    $managername = $_POST['managerName'];
    $mail = $_POST['email'];
    $phone = $_POST['managertel'];
    $jobdecribtion = $_POST['jobdescribtion'];   

$sql = "INSERT INTO companyform (id, Name, Field, Position, EmptyPositions
  , Address, ManagerName, ManagerEmail, LeastQualification, JobDescription,managerphone)
VALUES ('','$name','$field','$position','$emptypositions','$address','$managername','$mail','$qualify','$jobdecribtion','$phone')";


if ($conn->query($sql) == TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}
//include("ApplyForm.html");
header("refresh:2; url=AddJob.html");
//}
    ?>
    
