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

    $fname = $_POST['FirstName'];
    $lname = $_POST['LastName']; 
    $date = $_POST['myDate'];
    $mail = $_POST['email'];
    $phone = $_POST['tel'];
    $address = $_POST['address'];
    $field = $_POST['fields'];
    $company = $_POST['company'];
    $position = $_POST['position'];
    
     
         
      
        
 //if(isset($_POST['submit'])){
     
      $name = $_FILES['myfile']['name'];
          $type = $_FILES['myfile']['type'];
    // if(!empty($_FILES['myfile']['tmp_name']) 
     //&& file_exists($_FILES['myfile']['tmp_name'])) {
    $data= file_get_contents($_FILES['myfile']['tmp_name']);
//}
         // $data = addslashes(file_get_contents($_FILES['myfile']['tmp_name']));
          $stmt = $conn->prepare("insert into employeeform values('',?,?,?,?,?,?,?,?,?,?,'0')");
          $stmt ->bind_param("ssssssssss",$fname,$lname,$mail,$date,$data,$address,$phone,$field,$company,$position);
          $stmt->execute();
// }
           
/*$sql = "INSERT INTO employeeform (Name, Email,Birthdate ,CV, Address, PhoneNumber, Field, Company, Position)
VALUES ('$fname$space$lname','$mail','$date','ok','$address','$phone','$field','$company','$position')";


if ($conn->query($sql) == TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}*/
//include("ApplyForm.html");
header("refresh:2; url=ApplyForm.html");
//}

?>


