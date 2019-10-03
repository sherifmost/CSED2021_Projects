<html>

<?php
include "connectdb.php";
$query = "SELECT * FROM companyform WHERE Name = '".$_POST["Name"]."'";    
 $result = $conn->query($query);
?>
<option value="">Select Position</option>
    <?php
            while($row = $result->fetch_assoc()){
                ?>
                <option value="<?php echo $row["Position"]; ?>"> <?php echo $row["Position"];?>  </option>
            <?php
            }
            ?>
    
</html>
