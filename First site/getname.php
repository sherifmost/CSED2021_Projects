<html>

<?php
include "connectdb.php";
$query = "SELECT * FROM companyform WHERE Field = '".$_POST["Field"]."'";    
 $result = $conn->query($query);
?>
<option value="">Select Company</option>
    <?php
            while($row = $result->fetch_assoc()){
                ?>
                <option value="<?php echo $row["Name"]; ?>"> <?php echo $row["Name"];?>  </option>
            <?php
            }
            ?>
    
</html>
